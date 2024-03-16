package com.learning.shopdevjava.service;

import com.learning.shopdevjava.constant.ErrorCodeConstant;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.InsufficientPermissionException;
import com.learning.shopdevjava.exception.NotFoundException;
import com.learning.shopdevjava.factory.product.ProductCreator;
import com.learning.shopdevjava.helper.ProductFactoryHelper;
import com.learning.shopdevjava.repository.ClothRepository;
import com.learning.shopdevjava.repository.ElectronicRepository;
import com.learning.shopdevjava.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClothRepository clothRepository;
    @Autowired
    private ElectronicRepository electronicRepository;

    @Autowired
    private ProductFactoryHelper productFactoryHelper;

    public ProductEntity createProduct(String type, ProductDTO dto){
        ProductCreator creator = productFactoryHelper.get(type);
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

    public List<ProductDTO> getProductByIds(List<ObjectId> productIds, int offset, int limit){
        if(CollectionUtils.isEmpty(productIds)){
            return new ArrayList<>();
        }
        List<ProductEntity> byIdIn = productRepository.findByIdInAndIsPublishedIsTrueOrderByUpdatedAtDesc(productIds, PageRequest.of(offset, limit));

        List<ProductDTO> res = new ArrayList<>();
        for(ProductEntity entity: byIdIn){
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

    public List<ProductDTO> fulltextSearchProduct(String text, int offset, int limit){
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(text);

        List<ProductEntity> entities = productRepository.findByIsPublishedIsTrue(criteria, PageRequest.of(offset, limit));
        List<ProductDTO> dtos = new ArrayList<>();
        for(ProductEntity entity : entities){
            dtos.add(ProductDTO.fromEntity(entity));
        }
        return dtos;
    }

    public ProductEntity patchUpdate(String shopId, String productId, Map<String, Object> data){
        Optional<ProductEntity> byId = productRepository.findById(new ObjectId(productId));
        if(!byId.isPresent()){
            throw new NotFoundException("Product not found. Id=" + productId);
        }
        ProductEntity entity = byId.get();
        if(!shopId.equals(entity.getProductShop())){
            throw new InsufficientPermissionException(ErrorCodeConstant.INSUFFICIENT_PERMISSION);
        }

        ProductCreator factory = productFactoryHelper.get(entity.getProductType());
        ProductEntity afterUpdate = factory.patchUpdateProduct(entity, data);

        return afterUpdate;
    }

}
