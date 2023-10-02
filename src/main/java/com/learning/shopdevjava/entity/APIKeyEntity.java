package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("APIKey")
@Data
@Builder
public class APIKeyEntity {
    private String key;
    private boolean status;
    private List<String> permissions;
}
