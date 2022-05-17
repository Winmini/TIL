package com.webflux.practice.service;

import lombok.Data;
import reactor.core.publisher.Flux;

public class KitchenService {
	Flux<Dish> getDishes() {
		return Flux.just(new Dish("chicken"), new Dish("meat"), new Dish("burger"));
	}

	@Data
	static class Dish {
		private String name;
		private boolean delivered = false;
		public Dish(String name) {
			this.name = name;
		}

		public Dish deliver(Dish dish) {
			delivered = true;
			return this;
		}
	}
}
