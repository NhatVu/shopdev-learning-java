package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.KeyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyRepository extends MongoRepository<KeyEntity, String> {
}
