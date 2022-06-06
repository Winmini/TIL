package com.ecommerce.computer.service;

import com.ecommerce.computer.domain.Item;
import org.springframework.stereotype.Service;

import com.ecommerce.computer.domain.Cart;
import com.ecommerce.computer.domain.CartItem;
import com.ecommerce.computer.repository.CartRepository;
import com.ecommerce.computer.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;

	@Override
	public Mono<Cart> addToCart(String cartId, String itemName) {

		return cartRepository.findById(cartId)
			.defaultIfEmpty(Cart.builder().id(cartId).build())
			.flatMap(cart -> cart.getCartItems().stream()
				.filter(cartItem -> cartItem.getItem().getName().equals(itemName))
				.findAny()
				.map(cartItem -> {
					cartItem.increment();
					return Mono.just(cart);
				})
				.orElseGet(() -> itemRepository.findByName(itemName)
					.map(CartItem::new)
					.doOnNext(cartItem -> cart.getCartItems().add(cartItem))
					.map(cartItem -> cart)))
			.flatMap(cartRepository::save);
	}

	@Override
	public Mono<Cart> getCart(String cartId) {
		return cartRepository.findById(cartId);
	}

	@Override
	public Flux<Item> getInventory() {
		return itemRepository.findAll();
	}

}
