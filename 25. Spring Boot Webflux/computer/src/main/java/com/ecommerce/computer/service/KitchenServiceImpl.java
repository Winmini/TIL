package com.ecommerce.computer.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ecommerce.computer.domain.Dish;

import reactor.core.publisher.Flux;

@Service
public class KitchenServiceImpl implements KitchenService{

	private final List<Dish> menu = List.of(new Dish("Sesame chicken"),
		new Dish("Lo mein noodles, plain"),
		new Dish("Sweet & sour beef"));
	private final Random picker = new Random();

	/**
	 * 요리 스트림 생성
	 */
	@Override
	public Flux<Dish> getDishes() {

		return Flux.<Dish> generate(sink -> sink.next(randomDish()))
			.delayElements(Duration.ofMillis(250));
	}

	private Dish randomDish() {
		return menu.get(picker.nextInt(menu.size()));
	}
}
