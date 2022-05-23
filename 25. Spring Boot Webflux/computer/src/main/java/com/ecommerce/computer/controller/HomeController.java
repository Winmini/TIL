package com.ecommerce.computer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computer.domain.Cart;
import com.ecommerce.computer.domain.Item;
import com.ecommerce.computer.repository.CartRepository;
import com.ecommerce.computer.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HomeController {

	@GetMapping
	public Flux<Item> home() {
		return null;
	}
}
