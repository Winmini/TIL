package com.webflux.practice.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.SuccessCallback;

public class FutureTest {

	Logger log = LoggerFactory.getLogger(FutureTest.class);

	@Test
	void basic() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(() -> {
			try {
				callFunction(2000);
			} catch (Exception e) {

			}
		});

		Future<String> future = es.submit(() -> {
			Thread.sleep(1000);
			return "Hello";
		});

		log.debug(future.get());
		log.debug("Exit");

		Thread.sleep(2000);
	}

	private void callFunction(int millis) throws InterruptedException {
		log.debug("start call");
		Thread.sleep(millis);
		log.debug("end call");
	}

	@Test
	void future() {
		ExecutorService es = Executors.newCachedThreadPool();
		FutureTask<String> f = new FutureTask<>(() -> {
			Thread.sleep(1000);
			return "Hello";
		}) {
			@Override
			protected void done() {
				try {
					System.out.println(get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		es.execute(f);
		es.shutdown(); // 셧다운 해야함
	}

	@Test
	void callbackFuture() throws InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();
		CallbackFutureTask callbackFutureTask = new CallbackFutureTask(() -> {
			Thread.sleep(1000);
			return "Hello";
		}, System.out::println);

		es.execute(callbackFutureTask);
		System.out.println("Hi");
		Thread.sleep(2000);
	}
}
