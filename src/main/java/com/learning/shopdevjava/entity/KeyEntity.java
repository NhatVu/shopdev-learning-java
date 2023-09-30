package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Keys")
@Data
@Builder
public class KeyEntity {
    @Id
    private String id;
    private String userId; // this should be shopId, but just follow tutorial first
    private String privateKey;
    private String publicKey;
    private String refreshToken;
}
