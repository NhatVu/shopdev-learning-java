package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.ProductEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, ObjectId> {
}
