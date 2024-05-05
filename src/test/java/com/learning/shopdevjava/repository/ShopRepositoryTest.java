package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.config.ShopStatusEnum;
import com.learning.shopdevjava.entity.ShopEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private ShopEntity shop;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        mongoTemplate.dropCollection(ShopEntity.class);

        // Create a sample shop entity
        shop = ShopEntity.builder()
                .name("Test Shop")
                .status(ShopStatusEnum.ACTIVE.getValue())
                .build();

    }

    @Test
    void shouldSaveShop() {
        // Save the shop
        ShopEntity savedShop = shopRepository.save(shop);

        // Verify the saved shop
        assertNotNull(savedShop.getId());
        assertEquals(shop.getName(), savedShop.getName());
        assertEquals(shop.getStatus(), savedShop.getStatus());
    }

    @Test
    void shouldFindAllShops() {
        // Save two shop entities
        ShopEntity shop1 = ShopEntity.builder()
                .name("Shop 1")
                .status(ShopStatusEnum.ACTIVE.getValue())
                .build();
        ShopEntity shop2 = ShopEntity.builder()
                .name("Shop 2")
                .status(ShopStatusEnum.DEACTIVE.getValue())
                .build();
        shopRepository.save(shop1);
        shopRepository.save(shop2);

        // Find all shops
        List<ShopEntity> shops = shopRepository.findAll();

        // Verify the list of shops
        assertEquals(2, shops.size());
        assertTrue(shops.stream().anyMatch(s -> s.getName().equals("Shop 1")));
        assertTrue(shops.stream().anyMatch(s -> s.getName().equals("Shop 2")));
    }

    @Test
    void shouldFindShopById() {
        // Save the shop
        ShopEntity savedShop = shopRepository.save(shop);

        // Find the shop by ID
        Optional<ShopEntity> foundShop = shopRepository.findById(savedShop.getId());

        // Verify the found shop
        assertTrue(foundShop.isPresent());
        assertEquals(savedShop.getId(), foundShop.get().getId());
        assertEquals(savedShop.getName(), foundShop.get().getName());
    }

    @Test
    void shouldDeleteShopById() {
        // Save the shop
        ShopEntity savedShop = shopRepository.save(shop);

        // Delete the shop by ID
        shopRepository.deleteById(savedShop.getId());

        // Verify the shop is deleted
        Optional<ShopEntity> deletedShop = shopRepository.findById(savedShop.getId());
        assertTrue(deletedShop.isEmpty());
    }
}
