package com.async.hello.controller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

	Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();

	@GetMapping("/dr")
	public DeferredResult<String> async() {
		log.debug("dr");
		DeferredResult<String> deferredResult = new DeferredResult<>();
		results.add(deferredResult);
		return deferredResult;
	}

	@GetMapping("/dr/count")
	public String countDr() {
		return String.valueOf(results.size());
	}

	@GetMapping("/dr/event")
	public String eventDr(String msg) {
		for (DeferredResult<String> result : results) {
			result.setResult("Hello" + msg);
			results.remove(result);
		}
		return "OK";
	}
}
