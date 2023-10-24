package com.learning.shopdevjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.learning.shopdevjava.repository")
@EnableMongoAuditing
public class ShopdevJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopdevJavaApplication.class, args);
	}

}
