package com.learning.shopdevjava.security;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PrivatePublicKeyPair {
    private String privateKey;
    private String publicKey;
}
