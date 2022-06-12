package com.ecommerce.computer.service;

import com.ecommerce.computer.domain.Cart;

import com.ecommerce.computer.domain.Item;
import com.ecommerce.computer.repository.item.SearchParameter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InventoryService {
	Mono<Cart> addToCart(String cartId, String itemName);

	Mono<Cart> getCart(String cartId);

	Flux<Item> getInventory(SearchParameter searchParameter);
}
