package com.learning.shopdevjava.dto;

import com.learning.shopdevjava.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
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

    public ProductEntity toEntity(){
        ProductEntity.ProductEntityBuilder productEntityBuilder = ProductEntity.builder()
                .productName(this.productName)
                .productThumb(this.productThumb)
                .productDescription(this.productDescription)
                .productPrice(this.productPrice)
                .productQuantity(this.productQuantity)
                .productType(this.productType)
                .productShop(this.productShop)
                .productAttributes(this.productAttributes);
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
                .build();

    }
}

