package com.learning.shopdevjava.entity;

import com.learning.shopdevjava.helper.Utils;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Document("Products")
@Data
@Builder
@ToString
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
    private String productSlug; // quan-jean-cao-cap. Adding '-' to string, to make an url
    private Object productAttributes; // need to clear about this field
    // more
    private float ratingAverage;
    private Object[] productVariation;
    private boolean isDraft;
    private boolean isPublished;
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // this is for default field and custom setter method in Lombok.
    public static class ProductEntityBuilder{
        private float ratingAverage=4.5f;
        private boolean isDraft=true;
        private boolean isPublished=false;

        public ProductEntityBuilder ratingAverage(float ratingAverage) {
            this.ratingAverage = 1.0f * Math.round(ratingAverage * 10)/10;
            return this;
        }

        public ProductEntityBuilder productName(String productName){
            this.productName = productName;
            this.productSlug = Utils.toSlug(productName);
            return this;
        }
    }

    public static void main(String[] args) {
        ProductEntity entity = ProductEntity.builder()
                .productName("product name")
//                .ratingAverage(4.3455556f)
                .productPrice(10)
                .build();
        System.out.println(entity);
    }
}

