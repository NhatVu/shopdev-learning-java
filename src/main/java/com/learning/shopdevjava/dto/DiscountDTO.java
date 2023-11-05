package com.learning.shopdevjava.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.learning.shopdevjava.config.StringConstant;
import com.learning.shopdevjava.entity.DiscountEntity;
import com.learning.shopdevjava.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
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
    private ObjectId shopId;
    @JsonProperty("isActive")
    private boolean isActive;
    private String discountAppliesTo;
    private List<String> discountProductIds;

    public DiscountEntity toEntity(){
        DiscountEntity.DiscountEntityBuilder builder = DiscountEntity.builder()
                .discountName(this.discountName)
                .discountDescription(this.discountDescription)
                .discountType(this.discountType)
                .discountValue(this.discountValue)
                .discountCode(this.discountCode)
                .discountStartDate(this.discountStartDate)
                .discountEndDate(this.discountEndDate)
                .discountMaxUses(this.discountMaxUses)
                .discountUsesCount(this.discountUsesCount)
                .discountUserUsed(this.discountUserUsed)
                .discountMaxUsesPerUser(this.discountMaxUsesPerUser)
                .discountMinOrderValue(this.discountMinOrderValue)
                .shopId(this.shopId)
                .isActive(this.isActive)
                .discountAppliesTo(this.discountAppliesTo)
                .discountProductIds(this.discountProductIds);
        if(this.id != null){
            builder.id(this.id);
        }
        return builder.build();
    }

    public static DiscountDTO fromEntity(DiscountEntity entity){
        return DiscountDTO.builder()
                .id(entity.getId())
                .discountName(entity.getDiscountName())
                .discountDescription(entity.getDiscountDescription())
                .discountType(entity.getDiscountType())
                .discountValue(entity.getDiscountValue())
                .discountCode(entity.getDiscountCode())
                .discountStartDate(entity.getDiscountStartDate())
                .discountEndDate(entity.getDiscountEndDate())
                .discountMaxUses(entity.getDiscountMaxUses())
                .discountUsesCount(entity.getDiscountUsesCount())
                .discountUserUsed(entity.getDiscountUserUsed())
                .discountMaxUsesPerUser(entity.getDiscountMaxUsesPerUser())
                .discountMinOrderValue(entity.getDiscountMinOrderValue())
                .shopId(entity.getShopId())
                .isActive(entity.isActive())
                .discountAppliesTo(entity.getDiscountAppliesTo())
                .discountProductIds(entity.getDiscountProductIds()).build();
    }
}
