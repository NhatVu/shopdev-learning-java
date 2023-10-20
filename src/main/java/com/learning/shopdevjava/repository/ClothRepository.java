package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.ClothEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothRepository extends MongoRepository<ClothEntity, ObjectId> {
}
