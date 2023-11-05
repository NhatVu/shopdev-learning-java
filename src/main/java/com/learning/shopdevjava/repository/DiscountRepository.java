package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.DiscountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscountRepository extends MongoRepository<DiscountEntity, ObjectId> {
    DiscountEntity findByShopIdAndDiscountCode(ObjectId shopId, String discountCode);
}
