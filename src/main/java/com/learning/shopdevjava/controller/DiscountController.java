package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.dto.DiscountDTO;
import com.learning.shopdevjava.dto.ResponseObject;
import com.learning.shopdevjava.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/discount/")
@Slf4j
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @PostMapping
    public ResponseObject createDiscountCode(@RequestBody DiscountDTO dto, @RequestAttribute("userId") String shopId){
        dto.setShopId(new ObjectId(shopId));
        dto.setId(null);
        log.info(dto.toString());

        return ResponseObject.builder().code(201)
                .metadata(dto)
                .message("afc")
                .build();
//        return discountService.createDiscountCode(dto);
    }
}
