package com.webflux.practice.service;

public class PoliteRestaurant {

	public static void main(String[] args) {
		SimpleServer server = new SimpleServer(new KitchenService());

		server.doPoliteMyJob().subscribe(dish -> System.out.println("Consuming " + dish), System.err::println);
	}
}
