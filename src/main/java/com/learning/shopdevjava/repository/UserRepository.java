package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.security.Key;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
}
