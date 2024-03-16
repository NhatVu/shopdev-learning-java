package com.learning.shopdevjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.learning.shopdevjava.entity.DiscountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private String id;
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
    private String shopId;
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
                .shopId(new ObjectId(this.shopId))
                .isActive(this.isActive)
                .discountAppliesTo(this.discountAppliesTo);
        if (this.discountProductIds != null) {
            builder.discountProductIds(this.discountProductIds.stream().map(e -> new ObjectId(e)).collect(Collectors.toList()));
        }
        if(this.id != null){
            builder.id(new ObjectId(this.id));
        }
        return builder.build();
    }

    public static DiscountDTO fromEntity(DiscountEntity entity){
        DiscountDTOBuilder builder = DiscountDTO.builder()
                .id(entity.getId().toString())
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
                .shopId(entity.getShopId().toString())
                .isActive(entity.isActive())
                .discountAppliesTo(entity.getDiscountAppliesTo());
        if (entity.getDiscountProductIds() != null) {
            builder.discountProductIds(entity.getDiscountProductIds().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        return builder.build();
    }
}
