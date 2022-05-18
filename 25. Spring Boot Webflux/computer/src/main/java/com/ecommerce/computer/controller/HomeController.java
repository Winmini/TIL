package com.ecommerce.computer.controller;

import static org.springframework.http.MediaType.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computer.domain.Dish;
import com.ecommerce.computer.service.KitchenService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class HomeController {

	private final KitchenService service;

	@GetMapping(value = "/server", produces = TEXT_EVENT_STREAM_VALUE)
	public Flux<Dish> serveDishes() {
		return service.getDishes();
	}

	@GetMapping( value = "/served-dishes", produces = TEXT_EVENT_STREAM_VALUE)
	public Flux<Dish> deliverDish() {
		return service.getDishes()
			.map(Dish::deliver);
	}

}
