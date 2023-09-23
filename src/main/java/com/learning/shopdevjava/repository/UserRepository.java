package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.security.Key;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
