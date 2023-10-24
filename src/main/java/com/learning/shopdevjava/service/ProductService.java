package com.learning.shopdevjava.service;

import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.CreationException;
import com.learning.shopdevjava.exception.InsufficientPermissionException;
import com.learning.shopdevjava.exception.NotFoundException;
import com.learning.shopdevjava.factory.product.ClothCreator;
import com.learning.shopdevjava.factory.product.ElectronicCreator;
import com.learning.shopdevjava.factory.product.ProductCreator;
import com.learning.shopdevjava.repository.ClothRepository;
import com.learning.shopdevjava.repository.ElectronicRepository;
import com.learning.shopdevjava.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<ProductDTO> getAllDraftProduct(String shopId, int offset, int limit){
        List<ProductEntity> listEntity = productRepository.findByIsDraftIsTrueAndProductShopOrderByUpdatedAtDesc(shopId, PageRequest.of(offset, limit));

        List<ProductDTO> res = new ArrayList<>();
        for(ProductEntity entity: listEntity){
            res.add(ProductDTO.fromEntity(entity));
        }
        return res;
    }

    public List<ProductDTO> getAllPublishProduct(String shopId, int offset, int limit){
        List<ProductEntity> listEntity = productRepository.findByIsPublishedIsTrueAndProductShopOrderByUpdatedAtDesc(shopId, PageRequest.of(offset, limit));

        List<ProductDTO> res = new ArrayList<>();
        for(ProductEntity entity: listEntity){
            res.add(ProductDTO.fromEntity(entity));
        }
        return res;
    }

    public ProductEntity publishProductByShop(String productId, String shopId){
        Optional<ProductEntity> byId = productRepository.findById(new ObjectId(productId));
        if(!byId.isPresent()){
            throw new NotFoundException("Product not found. Id=" + productId);
        }
        ProductEntity entity = byId.get();
        if(!shopId.equals(entity.getProductShop())){
            throw new InsufficientPermissionException(ErrorCodeConstant.INSUFFICIENT_PERMISSION);
        }

        entity.setDraft(false);
        entity.setPublished(true);
        return productRepository.save(entity);
    }

    public ProductEntity unPublishProductByShop(String productId, String shopId){
        Optional<ProductEntity> byId = productRepository.findById(new ObjectId(productId));
        if(!byId.isPresent()){
            throw new NotFoundException("Product not found. Id=" + productId);
        }
        ProductEntity entity = byId.get();
        if(!shopId.equals(entity.getProductShop())){
            throw new InsufficientPermissionException(ErrorCodeConstant.INSUFFICIENT_PERMISSION);
        }

        entity.setDraft(true);
        entity.setPublished(false);
        return productRepository.save(entity);
    }


}
