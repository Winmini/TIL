package com.ecommerce.computer.domain;

import lombok.Getter;
import lombok.ToString;
import reactor.core.publisher.Mono;

@Getter
@ToString
public class CartItem {

	private Item item;
	private int quantity;

	public  CartItem(Item item) {
		this.item = item;
		quantity = 1;
	}

	public void increment() {
		quantity++;
	}
}
