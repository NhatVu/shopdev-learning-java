package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.KeyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends MongoRepository<KeyEntity, String> {
    KeyEntity findByUserId(String userId);
}
