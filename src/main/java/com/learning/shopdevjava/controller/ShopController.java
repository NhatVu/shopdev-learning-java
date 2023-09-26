package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import com.learning.shopdevjava.dto.ShopDTO;
import com.learning.shopdevjava.entity.ShopEntity;
import com.learning.shopdevjava.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/shop/")
@AllArgsConstructor
public class ShopController {

    private ShopRepository shopRepository;

    @PostMapping("signup")
    public Map<String, Object> signUp(@RequestBody ShopDTO shopDTO){
        // note: handle case can't parse dto
        ShopEntity entity = ShopEntity.builder()
                .name(shopDTO.getName())
                .email(shopDTO.getEmail())
                .password(shopDTO.getPassword())
                .status(shopDTO.getStatus())
                .roles(shopDTO.getRoles())
                .build();

        shopRepository.save(entity);

        // response
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("metadata", entity);
        return res;
    }

    @GetMapping("get-shop")
    public List<ShopDTO> getShop(@RequestParam String name){
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("name should not be empty");
        }
        List<ShopEntity> byName = shopRepository.findByName(name);
        return ShopDTO.fromEntityWithLimitFields(byName);
    }
}
