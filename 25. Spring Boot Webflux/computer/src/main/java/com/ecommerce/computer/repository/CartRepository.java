package com.ecommerce.computer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computer.domain.Cart;

import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}
