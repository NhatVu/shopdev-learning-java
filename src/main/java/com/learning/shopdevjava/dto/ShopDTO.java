package com.learning.shopdevjava.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.shopdevjava.entity.ShopEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopDTO {
    private String name;
    private String email;
    private String password;

    private String status;

    private boolean verify;

    private List<String> roles;

    public static ShopDTO fromEntityWithLimitFields(ShopEntity entity){
        return ShopDTO.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .verify(entity.isVerify())
                .build();
    }

    public static List<ShopDTO> fromEntityWithLimitFields(List<ShopEntity> entities){
        List<ShopDTO> res = new ArrayList<>();
        for(ShopEntity entity: entities){
            res.add(fromEntityWithLimitFields(entity));
        }
        return res;
    }
}
