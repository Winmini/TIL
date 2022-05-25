package com.ecommerce.computer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Dish {

	private final String name;
	private boolean delivered = false;

	public static Dish deliver(Dish dish) {
		Dish deliveredDish = new Dish(dish.name);
		deliveredDish.delivered = true;
		return deliveredDish;
	}

	public boolean isDelivered() {
		return delivered;
	}
}
