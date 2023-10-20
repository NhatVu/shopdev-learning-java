package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Clothes")
@Data
@Builder
public class ClothEntity {
    @Id
    private ObjectId id;
    private String brand;
    private String size;
    private String material;
}
