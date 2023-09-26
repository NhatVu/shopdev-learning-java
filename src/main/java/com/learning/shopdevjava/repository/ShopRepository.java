package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.ShopEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShopRepository extends MongoRepository<ShopEntity, String> {
    List<ShopEntity> findByName(String name);
}
