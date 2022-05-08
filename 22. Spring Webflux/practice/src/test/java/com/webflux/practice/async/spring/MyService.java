package com.webflux.practice.async.spring;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class MyService {

	Logger log = LoggerFactory.getLogger(MyService.class);

	@Async
	public CompletableFuture<String> hello() throws InterruptedException {
		log.debug("start hello()");
		Thread.sleep(10000);
		return CompletableFuture.completedFuture("Hello");
	}
}
