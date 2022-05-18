package com.ecommerce.computer.service;

import com.ecommerce.computer.domain.Dish;

import reactor.core.publisher.Flux;

public interface KitchenService {
	Flux<Dish> getDishes();
}
