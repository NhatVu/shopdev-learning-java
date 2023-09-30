package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.ShopEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends MongoRepository<ShopEntity, String> {
    List<ShopEntity> findByName(String name);

    ShopEntity findByEmail(String email);

    Boolean existsByEmail(String email);
}
