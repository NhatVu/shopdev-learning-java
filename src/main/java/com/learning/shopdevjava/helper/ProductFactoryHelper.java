package com.learning.shopdevjava.helper;

import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.factory.product.ClothCreator;
import com.learning.shopdevjava.factory.product.ElectronicCreator;
import com.learning.shopdevjava.factory.product.ProductCreator;
import com.learning.shopdevjava.repository.ClothRepository;
import com.learning.shopdevjava.repository.ElectronicRepository;
import com.learning.shopdevjava.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFactoryHelper {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClothRepository clothRepository;
    @Autowired
    private ElectronicRepository electronicRepository;
    public ProductCreator get(String productType){
        ProductCreator factory = null;
        switch (productType){
            case "clothes":
                factory = new ClothCreator(productRepository, clothRepository);
                break;
            case "electronics":
                factory = new ElectronicCreator(productRepository, electronicRepository);
                break;
            default:
                throw new CreationException(ErrorCodeConstant.CREATION_INVALID_TYPE);
        }
        return factory;
    }
}
