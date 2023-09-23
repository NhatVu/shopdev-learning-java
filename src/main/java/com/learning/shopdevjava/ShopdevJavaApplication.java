package com.learning.shopdevjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.learning.shopdevjava.repository")
public class ShopdevJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopdevJavaApplication.class, args);
	}

}
