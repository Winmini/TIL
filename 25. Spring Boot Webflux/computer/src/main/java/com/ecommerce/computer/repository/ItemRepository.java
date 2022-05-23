package com.ecommerce.computer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computer.domain.Item;

import reactor.core.publisher.Mono;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {

	Mono<Item> findByName(String name);
}
