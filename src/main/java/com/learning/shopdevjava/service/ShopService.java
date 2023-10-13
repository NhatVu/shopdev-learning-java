package com.learning.shopdevjava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import static com.learning.shopdevjava.config.StringConstant.*;

import com.learning.shopdevjava.config.StringConstant;
import com.learning.shopdevjava.dto.ShopDTO;
import com.learning.shopdevjava.entity.KeyEntity;
import com.learning.shopdevjava.entity.ShopEntity;
import com.learning.shopdevjava.exception.ForbiddenException;
import com.learning.shopdevjava.exception.NotFoundException;
import com.learning.shopdevjava.exception.UnAuthorizedException;
import com.learning.shopdevjava.repository.KeyRepository;
import com.learning.shopdevjava.repository.ShopRepository;
import com.learning.shopdevjava.security.JsonWebTokenUtils;
import com.learning.shopdevjava.security.PasswordHashingUtils;
import com.learning.shopdevjava.security.PrivatePublicKeyPair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class ShopService {
    private ShopRepository shopRepository;
    private KeyRepository keyRepository;
    private PasswordHashingUtils passwordHashingUtils;
    private JsonWebTokenUtils jsonWebTokenUtils;
    public ShopEntity signUp(String name, String email, String password){
        /*
        - if email exits -> return error
        - hash password, create shop
        - create + save privateKey and publicKey
        - create accessToken, refreshToken.
         */

        // use exist function, which return boolean instead of get function. This will reduce network throughput
        if(shopRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already exists");
        }

        String salt = passwordHashingUtils.generateSalt();
        String hashPassword = passwordHashingUtils.hashpw(password, salt);

        // when creating shop, there is some default value
        ShopEntity entity = ShopEntity.builder()
                .name(name)
                .email(email)
                .salt(salt)
                .password(hashPassword)
                .status(ShopStatusEnum.DEACTIVE.getValue())
                .verify(false)
                .roles(Arrays.asList(ShopRolesEnum.SHOP.getValue()))
                .build();
        entity = shopRepository.save(entity);

        // create token
        PrivatePublicKeyPair keyPair = jsonWebTokenUtils.generateRSAPair();
        KeyEntity keyEntity = KeyEntity.builder()
                .userId(entity.getId())
                .privateKey(keyPair.getPrivateKey())
                .publicKey(keyPair.getPublicKey())
                .build();
        KeyEntity saveKey = keyRepository.save(keyEntity);
        if(saveKey == null){
            throw new RuntimeException("Error when saving keyPair");
        }

        return shopRepository.save(entity);
    }

    public ShopEntity findByEmail(String email){
        return shopRepository.findByEmail(email);
    }

    public Map<String, Object> login(String email, String password){
        ShopEntity entity = findByEmail(email);
        if(entity == null){
            throw new UnAuthorizedException("Email or Password is wrong");
        }

        boolean verifypw = passwordHashingUtils.verifypw(entity.getPassword(), password, entity.getSalt());
        if(!verifypw){
            throw new UnAuthorizedException("Email or Password is wrong");
        }

        // get keyPair
        KeyEntity keyPair = keyRepository.findByUserId(entity.getId());

        // save keypair to db
        Map<String, Object> tokenPayload = new HashMap<>();
        tokenPayload.put("userId", entity.getId()); // this is shopId
        tokenPayload.put("email", entity.getEmail());
        tokenPayload.put("timestamp", System.currentTimeMillis());
        tokenPayload.put("type", "accessToken");

        String accessToken = jsonWebTokenUtils.sign(tokenPayload, keyPair.getPrivateKey());

        tokenPayload = new HashMap<>();
        tokenPayload.put("userId", entity.getId()); // this is shopId
        tokenPayload.put("email", entity.getEmail());
        tokenPayload.put("timestamp", System.currentTimeMillis());
        tokenPayload.put("type", "refreshToken");

        String refreshToken = jsonWebTokenUtils.sign(tokenPayload, keyPair.getPrivateKey(), 60 * 24 * 30);
        keyPair.addToRefreshToken(refreshToken);
        keyRepository.save(keyPair);

        // return metadata, token
        Map<String, Object> res = new HashMap<>();
        res.put("shop", ShopDTO.fromEntityWithLimitFields(entity));
        res.put(ACCESS_TOKEN, accessToken);
        res.put(REFRESH_TOKEN, refreshToken);
        return res;
    }

    public Map<String, Object> logout(String userId){
        KeyEntity keyEntity = keyRepository.findByUserId(userId);
        if(keyEntity == null){
            throw new NotFoundException("userId not found");
        }

        keyEntity.clearFreshToken();
        keyRepository.save(keyEntity);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "logout success");
        return res;
    }

    public Map<String, Object> createNewToken(String refreshToken){
        DecodedJWT decodeToken = JWT.decode(refreshToken);
        Claim claim = decodeToken.getClaim(StringConstant.USER_ID);
        if(claim == null){
            throw new IllegalStateException("userId must exists in token payload");
        }
        String userId = claim.asString();

        // if refresh token is in refreshTokenUsed list -> invalid all existing refresh token, and return 403 Forbidden
        KeyEntity byRefreshTokenUsed = keyRepository.findByUserIdAndRefreshTokenUsed(userId, refreshToken);
        if(byRefreshTokenUsed != null){
            byRefreshTokenUsed.clearFreshToken();;
            keyRepository.save(byRefreshTokenUsed);
            throw new ForbiddenException(ErrorCodeConstant.FORBIDDEN);
        }
        // add refresh token into refreshTokenUsed, remove refreshToken from refreshToken list (don't need to to that, but it should be. Incase we need to list all active refreshToken)
        byRefreshTokenUsed = keyRepository.findByUserId(userId);
        if(!byRefreshTokenUsed.getRefreshTokens().contains(refreshToken)){
            throw new ForbiddenException(ErrorCodeConstant.FORBIDDEN);
        }
        byRefreshTokenUsed.addToRefreshTokenUsed(refreshToken);
        byRefreshTokenUsed.getRefreshTokens().remove(refreshToken); // ensure 1 refresh token can only use 1 time

        keyRepository.save(byRefreshTokenUsed);

        //create new pair access and refresh token
        ShopEntity entity = shopRepository.findById(userId).get();
        // save keypair to db
        Map<String, Object> tokenPayload = new HashMap<>();
        tokenPayload.put("userId", entity.getId()); // this is shopId
        tokenPayload.put("email", entity.getEmail());
        tokenPayload.put("timestamp", System.currentTimeMillis());
        tokenPayload.put("type", "accessToken");

        String newAccessToken = jsonWebTokenUtils.sign(tokenPayload, byRefreshTokenUsed.getPrivateKey());

        tokenPayload = new HashMap<>();
        tokenPayload.put("userId", entity.getId()); // this is shopId
        tokenPayload.put("email", entity.getEmail());
        tokenPayload.put("timestamp", System.currentTimeMillis());
        tokenPayload.put("type", "refreshToken");

        String newRefreshToken = jsonWebTokenUtils.sign(tokenPayload, byRefreshTokenUsed.getPrivateKey(), 60 * 24 * 30);
        byRefreshTokenUsed.addToRefreshToken(newRefreshToken);
        keyRepository.save(byRefreshTokenUsed);

        Map<String, Object> res = new HashMap<>();
        res.put(ACCESS_TOKEN, newAccessToken);
        res.put(REFRESH_TOKEN, newRefreshToken);
        return res;
    }
}
