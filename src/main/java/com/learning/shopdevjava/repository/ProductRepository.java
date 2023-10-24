package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.ProductEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, ObjectId> {
    List<ProductEntity> findByIsDraftIsTrueAndProductShopOrderByUpdatedAtDesc(String productShop, Pageable pageable);

    List<ProductEntity> findByIsPublishedIsTrueAndProductShopOrderByUpdatedAtDesc(String productShop, Pageable pageable);

}
