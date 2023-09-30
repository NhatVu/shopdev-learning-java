package com.learning.shopdevjava.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class EncryptionUtils {
   private RSAUtils rsaUtils;

    public String encrypt(String message, String privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey pvt = rsaUtils.parsePrivateKey(privateKey);

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, pvt);

        byte[] encryptedMessageBytes = encryptCipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        return encryptedMessage;
    }

    public String decrypt(String encyptedMessage, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PublicKey pub = rsaUtils.parsePublicKey(publicKey);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, pub);

        byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encyptedMessage));
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        return decryptedMessage;
    }

    public static void main(String[] args) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        RSAUtils rsaUtils = new RSAUtils();
        EncryptionUtils encryptionUtils = new EncryptionUtils(rsaUtils);

        String message = "vuminhnhat";

        PrivatePublicKeyPair keyPair = rsaUtils.generateRSAPair();
        String encrypt = encryptionUtils.encrypt(message, keyPair.getPrivateKey());
        System.out.println("encrypt: " + encrypt);
        String decrypt = encryptionUtils.decrypt(encrypt, keyPair.getPublicKey());
        System.out.println("decrypt: " + decrypt);
        assert message.equals(decrypt);
    }
}
