package com.learning.shopdevjava.utils;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class PasswordHashingUtils {

    public String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] encode = Base64.getEncoder().encode(salt);
        return new String(encode);
    }

    public String hashpw(String password, String salt){
        try {
            byte[] decodeSalt = Base64.getDecoder().decode(salt.getBytes());
            KeySpec spec = new PBEKeySpec(password.toCharArray(), decodeSalt, 20, 2048);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new String(Base64.getEncoder().encode(hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean verifypw(String hashPassword, String rawPassword, String salt){
        String newHash = hashpw(rawPassword, salt);
        return newHash.equals(hashPassword);
    }

}
