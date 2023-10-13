package com.learning.shopdevjava.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.entity.KeyEntity;
import com.learning.shopdevjava.exception.InvalidTokenException;
import com.learning.shopdevjava.repository.KeyRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class JsonWebTokenUtils {
    private RSAUtils rsaUtils;
    private KeyRepository keyRepository;

    public PrivatePublicKeyPair generateRSAPair(){
        return rsaUtils.generateRSAPair();
    }

    public String sign(Map<String, Object> payload, String rsaPrivateKey){
        return sign(payload, rsaPrivateKey, 1000);
    }

    public String sign(Map<String, Object> payload, String rsaPrivateKey, int mintue) {
        try {
            PrivateKey privateKey = rsaUtils.parsePrivateKey(rsaPrivateKey);
            Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) privateKey);

            Calendar expriedTime = Calendar.getInstance();
            expriedTime.add(Calendar.MINUTE, mintue);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(expriedTime.getTime())
                    .withPayload(payload)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error(exception.getMessage(), exception);
            // Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }

    public Map<String, Claim> verify(String token, String rsaPublicKey) {
        DecodedJWT decodedJWT;
        try {
            PublicKey publicKey = rsaUtils.parsePublicKey(rsaPublicKey);
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("auth0")
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getClaims();
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            log.error(exception.getMessage(), exception);
            throw new InvalidTokenException(ErrorCodeConstant.INVALID_TOKEN);
        }
    }

    public Map<String, Claim> verify(String token){
        DecodedJWT decode = JWT.decode(token);
        Claim claim = decode.getClaim("userId");
        if(claim == null){
            throw new IllegalStateException("userId must exists in token payload");
        }
        String userId = claim.asString();
        KeyEntity keyEntity = keyRepository.findByUserId(userId);
        if (keyEntity == null){
            throw new IllegalStateException("userId is wrong");
        }

        return verify(token, keyEntity.getPublicKey());
    }

    public static void main(String[] args) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", "1");
        payload.put("userName", "nhat");

        RSAUtils rsaUtils = new RSAUtils();
        JsonWebTokenUtils jsonWebTokenUtils = new JsonWebTokenUtils(rsaUtils, null);

        PrivatePublicKeyPair keyPair = jsonWebTokenUtils.generateRSAPair();
        System.out.println("keyPair private: " + keyPair.getPrivateKey());
        System.out.println("keyPair public: " + keyPair.getPublicKey());
        String token = jsonWebTokenUtils.sign(payload, keyPair.getPrivateKey());
        System.out.println();
        System.out.println(token);

        // verify
        Map<String, Claim> verify = jsonWebTokenUtils.verify(token, keyPair.getPublicKey());
        System.out.println("claims: \n" + verify );
    }
}
