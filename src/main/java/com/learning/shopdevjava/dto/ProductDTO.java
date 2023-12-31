package com.learning.shopdevjava.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.shopdevjava.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private String id;
    private String productName;
    private String productThumb;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    private String productType;
    private String productShop;
    private Object productAttributes; // need to clear about this field
    // more
    private float ratingAverage=4.5f;
    private Object[] productVariation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public ProductEntity toEntity(){
        ProductEntity.ProductEntityBuilder productEntityBuilder = ProductEntity.builder()
                .productName(this.productName)
                .productThumb(this.productThumb)
                .productDescription(this.productDescription)
                .productPrice(this.productPrice)
                .productQuantity(this.productQuantity)
                .productType(this.productType)
                .productShop(this.productShop)
                .productAttributes(this.productAttributes)
                .ratingAverage(this.ratingAverage)
                .productVariation(this.productVariation);
        if(!StringUtils.isEmpty(this.id)){
            productEntityBuilder.id(new ObjectId(this.id));
        }
        return productEntityBuilder.build();

    }

    public static ProductDTO fromEntity(ProductEntity entity){
        return ProductDTO.builder()
                .id(entity.getId().toString())
                .productName(entity.getProductName())
                .productThumb(entity.getProductThumb())
                .productDescription(entity.getProductDescription())
                .productPrice(entity.getProductPrice())
                .productQuantity(entity.getProductQuantity())
                .productType(entity.getProductType())
                .productShop(entity.getProductShop())
                .productAttributes(entity.getProductAttributes())
                .ratingAverage(entity.getRatingAverage())
                .productVariation(entity.getProductVariation())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }
}

