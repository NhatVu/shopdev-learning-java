package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.APIKeyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIKeyRepository extends MongoRepository<APIKeyEntity, String> {
    APIKeyEntity findByKey(String key);
}
