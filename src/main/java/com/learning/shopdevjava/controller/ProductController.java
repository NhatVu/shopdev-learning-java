package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.dto.ProductSearchDTO;
import com.learning.shopdevjava.dto.ResponseObject;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/publish/{product_id}")
    public ResponseObject publishProduct(@RequestAttribute("userId") String shopId, @PathVariable("product_id") String productId){
        ProductEntity entity = productService.publishProductByShop(productId, shopId);
        return ResponseObject.builder()
                .code(201)
                .metadata(ProductDTO.fromEntity(entity))
                .message("publish product")
                .build();
    }

    @PostMapping("/unPublish/{product_id}")
    public ResponseObject unPublishProduct(@RequestAttribute("userId") String shopId, @PathVariable("product_id") String productId){
        ProductEntity entity = productService.unPublishProductByShop(productId, shopId);
        return ResponseObject.builder()
                .code(201)
                .metadata(ProductDTO.fromEntity(entity))
                .message("unPublish product")
                .build();
    }


    @GetMapping("/draft/all")
    public ResponseObject getAllDraftProductForShop(@RequestAttribute("userId") String shopId,
                                                    @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
                                                    @RequestParam(name = "limit", required = false, defaultValue = "2") int limit){
        List<ProductDTO> res = productService.getAllDraftProduct(shopId, offset, limit);
        return ResponseObject.builder()
                .code(200)
                .metadata(res)
                .message("get all draft product")
                .build();
    }

    @GetMapping("/publish/all")
    public ResponseObject getAllPublishProductForShop(@RequestAttribute("userId") String shopId,
                                                      @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
                                                      @RequestParam(name = "limit", required = false, defaultValue = "2") int limit){
        List<ProductDTO> res = productService.getAllPublishProduct(shopId, offset, limit);
        return ResponseObject.builder()
                .code(200)
                .metadata(res)
                .message("get all draft product")
                .build();
    }

    @PostMapping("/search")
    public ResponseObject fulltextSearchProduct(@RequestBody ProductSearchDTO dto) {
        List<ProductDTO> res = productService.fulltextSearchProduct(dto.getText(), dto.getOffset(), dto.getLimit());
        return ResponseObject.builder()
                .code(200)
                .metadata(res)
                .message("full text search product")
                .build();
    }

    @PatchMapping("/{product_id}")
    public ResponseObject patchProduct(@RequestBody Map<String, Object> body, @RequestAttribute("userId") String shopId,
                                       @PathVariable("product_id") String productId){
        if(StringUtils.isEmpty(productId)){
            throw new IllegalArgumentException("product_id has not empty");
        }

        ProductEntity entity = productService.patchUpdate(shopId, productId, body);
        return ResponseObject.builder()
                .code(204)
                .metadata(entity)
                .build();
    }


}
