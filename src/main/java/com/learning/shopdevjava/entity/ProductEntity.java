package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Products")
@Data
@Builder
public class ProductEntity {
    @Id
    private ObjectId id;
    private String productName;
    private String productThumb;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    private String productType;
    private String productShop;
    private Object productAttributes; // need to clear about this field

}

