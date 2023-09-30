package com.learning.shopdevjava.service;

import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import com.learning.shopdevjava.dto.ShopDTO;
import com.learning.shopdevjava.entity.KeyEntity;
import com.learning.shopdevjava.entity.ShopEntity;
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

        String accessToken = jsonWebTokenUtils.sign(tokenPayload, keyPair.getPrivateKey());

        // return metadata, token
        Map<String, Object> res = new HashMap<>();
        res.put("shop", ShopDTO.fromEntityWithLimitFields(entity));
        res.put("accessToken", accessToken);
        return res;
    }
}
