package com.ecommerce.computer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computer.domain.Item;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class HomeController {

	@GetMapping
	public Flux<Item> home() {
		return null;
	}
}
