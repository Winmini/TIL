package com.async.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class MyController {

	AsyncRestTemplate rt = new AsyncRestTemplate();

	@GetMapping("/rest")
	public DeferredResult<String> rest(int idx){
		DeferredResult<String> dr = new DeferredResult<>();

		ListenableFuture<ResponseEntity<String>> future = rt.getForEntity("http://localhost:8081/service?req={req}",
			String.class, "hello" + idx);
		future.addCallback(s ->{
			dr.setResult(s.getBody() + "/work");
		}, ex -> {
			dr.setErrorResult(ex.getMessage());
		});
		return dr;
	}
}
