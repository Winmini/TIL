package com.async.hello;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureTest {
	Logger log = LoggerFactory.getLogger(CompletableFutureTest.class);

	@Test
	void completableTest() throws InterruptedException {
		CompletableFuture.supplyAsync(() -> {
			log.info("supplyAsync");
			return 1;
		}).thenApply(s -> {
			log.info("thenApply : {}", s);
			return s + 1;
		}).exceptionally(e -> 10).thenAccept(s -> log.info("thenAccept : {}", s));

		log.info("exit");

		ForkJoinPool.commonPool().shutdown();
		ForkJoinPool.commonPool().awaitTermination(3, TimeUnit.SECONDS);
	}

	@Test
	void completableAsyncTest() throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(10);
		CompletableFuture.supplyAsync(() -> {
			log.info("supplyAsync");
			return 1;}, es)
			.thenApplyAsync(s -> {
			log.info("thenApply : {}", s);
			return s + 1;}, es)
			.exceptionally(e -> 10)
			.thenAcceptAsync(s -> log.info("thenAccept : {}", s), es);

		log.info("exit");

		ForkJoinPool.commonPool().shutdown();
		ForkJoinPool.commonPool().awaitTermination(3, TimeUnit.SECONDS);
	}
}
