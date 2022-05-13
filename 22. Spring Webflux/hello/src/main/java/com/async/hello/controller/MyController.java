package com.async.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class MyController {

	AsyncRestTemplate rt = new AsyncRestTemplate();

	@GetMapping("/rest")
	public DeferredResult<String> rest(int idx) {
		DeferredResult<String> dr = new DeferredResult<>();

		Completion.from(rt.getForEntity("URL1", String.class))
			.andApply(s -> rt.getForEntity("URL2", String.class))
			.andError(Throwable::printStackTrace)
			.andAccept(s -> dr.setResult(s.getBody()));
		return dr;
	}

}

