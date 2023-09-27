package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import com.learning.shopdevjava.dto.LoginDTO;
import com.learning.shopdevjava.dto.ShopDTO;
import com.learning.shopdevjava.entity.ShopEntity;
import com.learning.shopdevjava.exception.NotFoundException;
import com.learning.shopdevjava.repository.ShopRepository;
import com.learning.shopdevjava.service.ShopService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private ShopService shopService;

    @PostMapping("signup")
    public Map<String, Object> signUp(@RequestBody ShopDTO shopDTO){
        // note: handle case can't parse dto
        ShopEntity entity = shopService.signUp(shopDTO.getName(), shopDTO.getEmail(), shopDTO.getPassword());

        // response
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("metadata", entity);
        return res;
    }

    @PostMapping("login")
    public Map<String, Object> login(@RequestBody LoginDTO loginDTO){
        /**
         * return accessToken, shopInfo
         */
        if(loginDTO == null || StringUtils.isEmpty(loginDTO.getEmail()) || StringUtils.isEmpty(loginDTO.getPassword())){
            throw new IllegalArgumentException("invalid request body");
        }

        Map<String, Object> shop = shopService.login(loginDTO.getEmail(), loginDTO.getPassword());

        // response
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("metadata", shop);
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
