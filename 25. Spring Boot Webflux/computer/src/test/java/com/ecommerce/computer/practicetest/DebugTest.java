package com.ecommerce.computer.practicetest;

import java.util.Random;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class DebugTest {


	@Test
	void debugTest() {
		Hooks.onOperatorDebug();
		Mono<Integer> source;

		if (new Random().nextBoolean()) {
			source = Flux.range(1, 10).elementAt(5);
		} else {
			source = Flux.just(1, 2, 3, 4).elementAt(5);
		}

		source.subscribeOn(Schedulers.parallel()).block();
	}
}
