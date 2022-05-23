package com.ecommerce.computer.service;

import com.ecommerce.computer.domain.Cart;

import reactor.core.publisher.Mono;

public interface CartService {

	Mono<Cart> addToCart(String cartId, String itemId);
}
