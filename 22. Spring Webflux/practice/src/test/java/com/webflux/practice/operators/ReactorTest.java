package com.webflux.practice.operators;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class ReactorTest {

	@Test
	void basic() {
		Flux.<Integer>create(e -> {
			e.next(1);
			e.next(2);
			e.complete();})
			.log()
			.map(e -> e*10)
			.log()
			.subscribe(System.out::println);
	}
}
