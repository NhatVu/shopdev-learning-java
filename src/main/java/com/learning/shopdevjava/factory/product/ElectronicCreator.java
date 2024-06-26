package com.learning.shopdevjava.factory.product;

import com.learning.shopdevjava.constant.ErrorCodeConstant;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ElectronicEntity;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.exception.UpdateException;
import com.learning.shopdevjava.helper.ProductHelper;
import com.learning.shopdevjava.repository.ElectronicRepository;
import com.learning.shopdevjava.repository.ProductRepository;

import java.util.Map;
import java.util.Optional;

public class ElectronicCreator extends ProductCreator{
    public ElectronicCreator(ProductRepository productRepository, ElectronicRepository electronicRepository){
        super(productRepository);
        this.electronicRepository = electronicRepository;
    }
    private ElectronicRepository electronicRepository;

    @Override
    public ProductEntity createProduct(ProductDTO dto) {
        // create product first
        // create clothes
        // need to put in 1 transaction to ensure the data integrity
        ProductEntity productEntity = super.createProduct(dto);
        if(productEntity == null){
            throw new CreationException(ErrorCodeConstant.CREATION_FAIL);
        }

        Map<String, Object> attributes = (Map<String, Object>)dto.getProductAttributes();
        ElectronicEntity electronicEntity = ElectronicEntity.builder()
                .manufacture(attributes.get("manufacture").toString())
                .color(attributes.get("color").toString())
                .model(attributes.get("model").toString())
                .build();
        electronicEntity.setId(productEntity.getId());
        ElectronicEntity electronic = electronicRepository.save(electronicEntity);
        if(electronic == null){
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

            Optional<ElectronicEntity> byId = electronicRepository.findById(entity.getId());
            ElectronicEntity electronicEntity = null;
            if(!byId.isPresent()){
                electronicEntity = ElectronicEntity.builder().build();
            }else{
                electronicEntity = byId.get();
            }
            ProductHelper.patchUpdateProductChild(electronicEntity, ElectronicEntity.class, productAttributes);
            electronicRepository.save(electronicEntity);
        }
        return productEntity;
    }
}

