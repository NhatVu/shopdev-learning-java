package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.ElectronicEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectronicRepository extends MongoRepository<ElectronicEntity, ObjectId> {
}
