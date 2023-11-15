package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.DiscountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DiscountRepository extends MongoRepository<DiscountEntity, ObjectId> {
    DiscountEntity findByShopIdAndDiscountCode(ObjectId shopId, String discountCode);
    List<DiscountEntity> findByShopIdAndIsActiveIsTrueOrderByUpdatedAtDesc(ObjectId shopId, Pageable pageable);
}
