package com.webflux.practice.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerTest {

	Logger log = LoggerFactory.getLogger(SchedulerTest.class);

	@Test
	void subscribeOn() {
		Publisher<Integer> publisher = getPublisher();

		Publisher<Integer> subOnPub = getSubOnPub(publisher);

		subOnPub.subscribe(getSubscriber());

		log.debug("exit");
	}

	@Test
	void publishOn() {
		Publisher<Integer> publisher = getPublisher();

		Publisher<Integer> pubOnPub = getPubOnPub(publisher);

		pubOnPub.subscribe(getSubscriber());

		log.debug("exit");
	}

	@Test
	void subscribeOnAndPublishOn() {
		Publisher<Integer> publisher = getPublisher();

		Publisher<Integer> subOnPub = getSubOnPub(publisher);
		Publisher<Integer> pubOnPub = getPubOnPub(subOnPub);

		pubOnPub.subscribe(getSubscriber());

		log.debug("exit");
	}

	private Publisher<Integer> getPubOnPub(Publisher<Integer> publisher) {
		return sub -> {
			publisher.subscribe(new Subscriber<>() {

				final ExecutorService es = Executors.newSingleThreadExecutor();

				@Override
				public void onSubscribe(Subscription s) {
					sub.onSubscribe(s);
				}

				@Override
				public void onNext(Integer integer) {
					es.execute(() -> sub.onNext(integer));
				}

				@Override
				public void onError(Throwable t) {
					es.execute(() -> sub.onError(t));
					es.shutdown();
				}

				@Override
				public void onComplete() {
					es.execute(sub::onComplete);
					es.shutdown();
				}
			});
		};
	}

	private Publisher<Integer> getPublisher() {
		return sub -> sub.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {
				log.debug("request");
				sub.onNext(1);
				sub.onNext(2);
				sub.onNext(3);
				sub.onNext(4);
				sub.onNext(5);
				sub.onComplete();
			}

			@Override
			public void cancel() {
			}
		});
	}
	private Publisher<Integer> getSubOnPub(Publisher<Integer> publisher) {
		return sub -> {
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.execute(() -> publisher.subscribe(sub));
		};
	}

	private Subscriber<Integer> getSubscriber() {
		return new Subscriber<Integer>() {
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
		};
	}



}
