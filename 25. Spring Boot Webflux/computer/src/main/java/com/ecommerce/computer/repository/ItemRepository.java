package com.ecommerce.computer.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computer.domain.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemRepository extends ReactiveCrudRepository<Item, String>, ReactiveQueryByExampleExecutor<Item> {

	Mono<Item> findByName(String name);

	Flux<Item> findByNameContaining(String partialName);

	<T extends Item> Mono<T> findOne(Example<T> var1);
}
