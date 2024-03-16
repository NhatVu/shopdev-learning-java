package com.learning.shopdevjava.factory.product;

import com.learning.shopdevjava.constant.ErrorCodeConstant;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.helper.ProductHelper;
import com.learning.shopdevjava.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public abstract class ProductCreator {
    @Autowired
    private ProductRepository productRepository;

    public ProductCreator(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(ProductDTO dto){
        ProductEntity entity = dto.toEntity();
        ProductEntity save = productRepository.save(entity);
        if(save == null){
            throw new CreationException(ErrorCodeConstant.CREATION_FAIL);
        }
        return save;
    };

    public ProductEntity patchUpdateProduct(ProductEntity entity, Map<String, Object> data){
        ProductHelper.patchUpdateProduct(entity, data);
        return productRepository.save(entity);
    }
}
