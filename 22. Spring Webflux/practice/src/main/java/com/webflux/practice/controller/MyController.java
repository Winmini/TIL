package com.webflux.practice.controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.reactive.function.client.WebClient;

import com.webflux.practice.service.MyService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

	private final MyService myService;
	private final WebClient client = WebClient.create();
	private final String URL = "http://localhost:8081/service?req={req}";

	@GetMapping("/test")
	public Flux<String> test() {
		log.info("start");
		Flux<String> mono = Flux.just("1", "2", "3").log();
		log.info("end");
		return mono;
	}

	@GetMapping("/rest")
	public Mono<String> async(int idx) {
		return client.get()
			.uri(URL, idx)
			.exchangeToMono(i -> i.bodyToMono(String.class));
	}

	@Data
	static class TestResponse {
		private Long id;
		private String value;
	}
}
