package com.ecommerce.computer.service;

import org.springframework.stereotype.Service;

import com.ecommerce.computer.domain.Cart;
import com.ecommerce.computer.domain.CartItem;
import com.ecommerce.computer.repository.CartRepository;
import com.ecommerce.computer.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

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

}
