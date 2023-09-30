package com.learning.shopdevjava.security;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtils {
    public PrivatePublicKeyPair generateRSAPair() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA"); // RSA256

            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            StringBuilder encodePrivatekey = new StringBuilder();
            encodePrivatekey.append(new String(Base64.getEncoder().encode(privateKey.getEncoded())));

            StringBuilder encodePublicKey = new StringBuilder();
            encodePublicKey.append(new String(Base64.getEncoder().encode(publicKey.getEncoded())));

            PrivatePublicKeyPair keyPair = PrivatePublicKeyPair.builder()
                    .privateKey(encodePrivatekey.toString())
                    .publicKey(encodePublicKey.toString())
                    .build();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey parsePrivateKey(String privateKey) {
        byte[] decodePrivateKey = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(decodePrivateKey);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");

            PrivateKey pvt = kf.generatePrivate(ks);
            return pvt;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey parsePublicKey(String publicKey) {
        byte[] decodePublicKey = Base64.getDecoder().decode(publicKey);
        try {
            X509EncodedKeySpec ks = new X509EncodedKeySpec(decodePublicKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pub = kf.generatePublic(ks);
            return pub;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
