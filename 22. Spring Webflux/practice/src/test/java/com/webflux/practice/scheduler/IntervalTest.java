package com.webflux.practice.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntervalTest {

	Logger log = LoggerFactory.getLogger(IntervalTest.class);

	@Test
	void Interval() {
		Publisher<Integer> pub = sub -> {
			sub.onSubscribe(new Subscription() {

				int no = 0;
				boolean canceled = false;

				@Override
				public void request(long n) {
					ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
					ses.scheduleAtFixedRate(() -> {
						if(canceled){
							ses.shutdown();
							return;
						}
						sub.onNext(no++);
					}, 0, 300, TimeUnit.MILLISECONDS);
				}

				@Override
				public void cancel() {
					canceled = true;
				}
			});
		};

		Publisher<Integer> takePub = sub -> {
			pub.subscribe(new Subscriber<>() {
				int counter = 0;
				final int MAX_VALUE = 5;
				Subscription subscription;

				@Override
				public void onSubscribe(Subscription s) {
					subscription = s;
					sub.onSubscribe(s);
				}

				@Override
				public void onNext(Integer integer) {
					sub.onNext(integer);
					if (++counter >= MAX_VALUE) {
						subscription.cancel();
					}
				}

				@Override
				public void onError(Throwable t) {
					sub.onError(t);
				}

				@Override
				public void onComplete() {
					sub.onComplete();
				}
			});
		};

		takePub.subscribe(new Subscriber<>() {
			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer integer) {
				log.debug("onNext:{}", integer);
			}

			@Override
			public void onError(Throwable t) {
				log.error("onError:", t);
			}

			@Override
			public void onComplete() {
				log.debug("onComplete");
			}
		});

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
