package com.learning.shopdevjava.factory.product;

import com.learning.shopdevjava.constant.ErrorCodeConstant;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ClothEntity;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.exception.UpdateException;
import com.learning.shopdevjava.helper.ProductHelper;
import com.learning.shopdevjava.repository.ClothRepository;
import com.learning.shopdevjava.repository.ProductRepository;

import java.util.Map;
import java.util.Optional;

public class ClothCreator extends ProductCreator {
    public ClothCreator(ProductRepository productRepository, ClothRepository clothRepository) {
        super(productRepository);
        this.clothRepository = clothRepository;
    }

    private ClothRepository clothRepository;

    @Override
    public ProductEntity createProduct(ProductDTO dto) {
        // create product first
        // create clothes
        // need to put in 1 transaction to ensure the data integrity
        ProductEntity productEntity = super.createProduct(dto);
        if (productEntity == null) {
            throw new CreationException(ErrorCodeConstant.CREATION_FAIL);
        }

        Map<String, Object> attributes = (Map<String, Object>) dto.getProductAttributes();
        ClothEntity clothEntity = ClothEntity.builder()
                .brand(attributes.get("brand").toString())
                .size(attributes.get("size").toString())
                .material(attributes.get("size").toString())
                .build();
        clothEntity.setId(productEntity.getId());
        ClothEntity cloth = clothRepository.save(clothEntity);
        if (cloth == null) {
            throw new CreationException(ErrorCodeConstant.CREATION_FAIL);
        }
        return productEntity;
    }

    @Override
    public ProductEntity patchUpdateProduct(ProductEntity entity, Map<String, Object> data) {
        ProductEntity productEntity = super.patchUpdateProduct(entity, data);
        if (productEntity == null) {
            throw new UpdateException(ErrorCodeConstant.UPDATE_FAIL);
        }
        if (data.get("productAttributes") != null) {
            // need to update child
            Map<String, Object> productAttributes = (Map<String, Object>) data.get("productAttributes");

            Optional<ClothEntity> byId = clothRepository.findById(entity.getId());
            ClothEntity clothEntity = null;
            if(!byId.isPresent()){
                clothEntity = ClothEntity.builder().build();
            }else{
                clothEntity = byId.get();
            }
            ProductHelper.patchUpdateProductChild(clothEntity, ClothEntity.class, productAttributes);
            clothRepository.save(clothEntity);
        }
        return productEntity;
    }
}
