package com.learning.shopdevjava.factory.product;

import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.config.ProductTypeEnum;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


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
}
