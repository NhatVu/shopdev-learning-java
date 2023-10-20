package com.learning.shopdevjava.service;

import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.factory.product.ClothCreator;
import com.learning.shopdevjava.factory.product.ElectronicCreator;
import com.learning.shopdevjava.factory.product.ProductCreator;
import com.learning.shopdevjava.repository.ClothRepository;
import com.learning.shopdevjava.repository.ElectronicRepository;
import com.learning.shopdevjava.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClothRepository clothRepository;
    @Autowired
    private ElectronicRepository electronicRepository;

    public ProductEntity createProduct(String type, ProductDTO dto){
        ProductCreator creator = null;
        switch (type){
            case "clothes":
                creator = new ClothCreator(productRepository, clothRepository);
                break;
            case "electronics":
                creator = new ElectronicCreator(productRepository, electronicRepository);
                break;
            default:
                throw new CreationException(ErrorCodeConstant.CREATION_INVALID_TYPE);
        }

        ProductEntity product = creator.createProduct(dto);
        return product;
    }
}
