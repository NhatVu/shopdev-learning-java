package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document("Discounts")
@Data
@Builder
public class DiscountEntity {
    @Id
    private ObjectId id;
    private String discountName;
    private String discountDescription;
    private String discountType; // fixed_amount or percentage
    private double discountValue; // actual price or percentage
    private String discountCode; // ma giam gia
    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;
    private int discountMaxUses; // max discount can be used
    private int discountUsesCount; // number of discount that have been used
    private List<String> discountUserUsed; // who used discount
    private int discountMaxUsesPerUser; // max discount per user
    private double discountMinOrderValue;
    @DBRef(db="Shops")
    private ObjectId shopId;
    private boolean isActive;
    private String discountAppliesTo;
    private List<String> discountProductIds;


}
