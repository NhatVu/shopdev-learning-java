package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.dto.DiscountDTO;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.dto.ResponseObject;
import com.learning.shopdevjava.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/discount/")
@Slf4j
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @PostMapping
    public ResponseObject createDiscountCode(@RequestBody DiscountDTO dto, @RequestAttribute("userId") String shopId){
        dto.setShopId(shopId);
        dto.setId(null);
        log.info(dto.toString());
        DiscountDTO discountCode = discountService.createDiscountCode(dto);

        return ResponseObject.builder().code(201)
                .metadata(discountCode)
                .message("afc")
                .build();
    }

    @GetMapping("getProduct")
    public ResponseObject getProductByDiscountCode(@RequestParam String discountCode,
                                                     @RequestAttribute("userId") String shopId,
                                                     @RequestParam int offset,
                                                     @RequestParam int limit){
        List<ProductDTO> res = discountService.getProductByDiscountCode(discountCode, shopId, offset, limit);
        return ResponseObject.builder().code(200)
                .metadata(res)
                .message("afc")
                .build();
    }

    @GetMapping("getDiscount")
    public ResponseObject getDiscountByShopId(@RequestAttribute("userId") String shopId,
                                              @RequestParam int offset,
                                              @RequestParam int limit){
        List<DiscountDTO> res = discountService.getDiscountByShopId(shopId, offset, limit);
        return ResponseObject.builder().code(200)
                .metadata(res)
                .message("afc")
                .build();
    }

    }
