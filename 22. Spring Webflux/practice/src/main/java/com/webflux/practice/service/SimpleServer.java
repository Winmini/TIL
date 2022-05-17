package com.webflux.practice.service;

import com.webflux.practice.service.KitchenService.Dish;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class SimpleServer {
	private final KitchenService kitchen;

	public Flux<Dish> doMyJob() {
		return kitchen.getDishes().map(dish -> dish.deliver(dish));
	}

	public Flux<Dish> doPoliteMyJob(){
		return kitchen.getDishes()
			.doOnNext(dish -> System.out.println("Thank you for " + dish + "!"))
			.doOnError(error -> System.out.println("So sorry about " + error.getMessage()))
			.doOnComplete(() -> System.out.println("Thanks for all your hard work!"))
			.map(dish -> dish.deliver(dish));
	}
}
