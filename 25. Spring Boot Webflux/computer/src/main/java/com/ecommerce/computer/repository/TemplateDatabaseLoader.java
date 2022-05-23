package com.ecommerce.computer.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.ecommerce.computer.domain.Item;

// @Component
public class TemplateDatabaseLoader {
//
// 	@Bean
// 	public CommandLineRunner initialize(MongoOperations mongo){
// 		return args -> {
// 			mongo.save(new Item.Builder().name("itemA").price(1000).build());
// 			mongo.save(new Item.Builder().name("itemB").price(2000).build());
// 		};
// 	}
}
