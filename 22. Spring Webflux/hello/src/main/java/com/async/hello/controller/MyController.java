package com.async.hello.controller;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
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
			.andAccept(s -> dr.setResult(s.getBody()));
		return dr;
	}

	public static class Completion {
		private Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> func;
		private Consumer<ResponseEntity<String>> con;
		private Completion next;

		public Completion(Consumer<ResponseEntity<String>> con) {
			this.con = con;
		}

		private Completion() {
		}

		public Completion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> func) {
			this.func = func;
		}

		public static Completion from(ListenableFuture<ResponseEntity<String>> lf) {
			Completion c = new Completion();
			lf.addCallback(c::complete, c::error);
			return c;

		}

		public void andAccept(Consumer<ResponseEntity<String>> con) {
			this.next = new Completion(con);
		}
		
		public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> func) {
			Completion c = new Completion(func);
			this.next = c;
			return c;
		}

		private void complete(ResponseEntity<String> s) {
			if (next != null) {
				next.run(s);
			}
		}

		private void run(ResponseEntity<String> value) {
			if (con != null) {
				con.accept(value);
			} else if (func != null) {
				ListenableFuture<ResponseEntity<String>> lf = func.apply(value);
				lf.addCallback(this::complete, this::error);
			}
		}

		private void error(Throwable e) {
		}
	}
}

