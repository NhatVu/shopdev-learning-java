package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("Keys")
@Data
@Builder
public class KeyEntity {
    @Id
    private String id;
    private String userId; // this should be shopId, but just follow tutorial first
    private String privateKey;
    private String publicKey;
    private List<String> refreshTokens;
    private List<String> refreshTokensUsed; // save used token, use to detect suspicious behaviour

    public boolean addToRefreshToken(String refreshToken){
        if(refreshTokens == null){
            refreshTokens = new ArrayList<>();
        }
        return refreshTokens.add(refreshToken);
    }

    public void clearFreshToken(){
        refreshTokens.clear();
    }

    public boolean addToRefreshTokenUsed(String refreshToken){
        if(refreshTokensUsed == null){
            refreshTokensUsed = new ArrayList<>();
        }
        return refreshTokensUsed.add(refreshToken);
    }
}
