package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.dto.ResponseObject;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/product/")
@Slf4j
public class ProductController {
    private ProductService productService;
    @PostMapping
    public ResponseObject creatProduct(@RequestBody ProductDTO dto, @RequestAttribute("userId") String shopId){
        dto.setProductShop(shopId);
        log.info("produdtDTO payload: " + dto);

        ProductEntity entity  = productService.createProduct(dto.getProductType(), dto);
        return ResponseObject.builder()
                .code(201)
                .metadata(ProductDTO.fromEntity(entity))
                .message("create product")
                .build();
    }
}
