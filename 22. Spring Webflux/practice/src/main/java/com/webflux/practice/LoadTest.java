package com.webflux.practice;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadTest {

	static AtomicInteger counter = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		ExecutorService es = Executors.newFixedThreadPool(100);
		RestTemplate rt = new RestTemplate();
		String url = "http://localhost:8080/rest?idx={idx}";

		CyclicBarrier barrier = new CyclicBarrier(101);

		StopWatch main = new StopWatch();
		main.start();

		for (int i = 0; i < 100; i++) {
			es.submit(() -> {
				int idx = counter.getAndAdd(1);
				barrier.await();
				StopWatch sw = new StopWatch();
				sw.start();

				String res = rt.getForObject(url, String.class, idx);

				sw.stop();
				log.info("Elapsed:{} {} / {}", idx, sw.getTotalTimeSeconds(), res);
				return null;
			});
		}
		barrier.await();
		es.shutdown();
		boolean a = es.awaitTermination(100, TimeUnit.SECONDS);
		main.stop();
		log.info("Total:{}", main.getTotalTimeSeconds());
	}
}