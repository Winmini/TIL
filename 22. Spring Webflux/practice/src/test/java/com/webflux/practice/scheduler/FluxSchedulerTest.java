package com.webflux.practice.scheduler;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxSchedulerTest {

	Logger log = LoggerFactory.getLogger(FluxSchedulerTest.class);

	@Test
	void scheduler() {
		Flux.range(1, 3)
			.publishOn(Schedulers.newSingle("pub"))
			.log()
			.subscribeOn(Schedulers.newSingle("sub"))
			.subscribe(System.out::println);

		log.debug("EXIT");
	}

	@Test
	void interval() {
		Flux.interval(Duration.ofMillis(500)).subscribe(i -> log.debug("onNext:{}", i));
	}

	@Test
	void wrong() {
		Executors.newSingleThreadExecutor().execute(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			}
			System.out.println("Hello");
		});
		System.out.println("EXIT");
	}
}
