package com.ecommerce.computer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computer.domain.Cart;
import com.ecommerce.computer.service.CartService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShoppingController {

	private final CartService cartService;

	@PostMapping("/add/{id}")
	public Mono<Cart> addItem(@PathVariable String id) {
		return cartService.addToCart("Youry Cart", "itemA");
	}
}
