package com.webflux.practice.operators;

import static java.util.stream.Collectors.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorTest {

	Logger log = (Logger)LoggerFactory.getLogger(OperatorTest.class);

	/**
	 * pub -> [data1] -> mapPub -> logSub
	 * <- subscribe(logSub)
	 * -> onSubscribe(s)
	 * -> onNext
	 * -> onNext
	 * -> onComplete or onError
	 */

	@Test
	void basic() {
		Iterable<Integer> iter = Stream.iterate(1, a -> a + 1).limit(10).collect(toList());

		Publisher<Integer> publisher = getPublisher(iter);
		// Publisher<Integer> mapPub = mapPub(publisher, s -> s * 10);
		// Publisher<Integer> minusMapPub = mapPub(mapPub, s -> -s);
		// Publisher<Integer> sumPub = sumPub(publisher);
		// Publisher<Integer> reducePub = reducePub(publisher, 0, Integer::sum);
		// reducePub.subscribe(logSubscriber());

	}

//	private Publisher<Integer> reducePub(Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> func) {
//		return sub -> pub.subscribe(new DelegateSub(sub){
//			int result = init;
//			@Override
//			public void onNext(Integer integer) {
//				result = func.apply(result, integer);
//			}
//
//			@Override
//			public void onComplete() {
//				sub.onNext(result);
//				sub.onComplete();
//			}
//		});
//	}

//	private Publisher<Integer> sumPub(Publisher<Integer> publisher) {
//		return sub -> publisher.subscribe(new DelegateSub(sub) {
//			int sum = 0;
//
//			@Override
//			public void onNext(Integer integer) {
//				sum += integer;
//			}
//
//			@Override
//			public void onComplete() {
//				sub.onNext(sum);
//				sub.onComplete();
//			}
//		});
//	}

	private Publisher<Integer> getPublisher(Iterable<Integer> iter) {
		return sub -> sub.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {
				try {
					iter.forEach(sub::onNext);
					sub.onComplete();
				} catch (Throwable t) {
					sub.onError(t);
				}
			}

			@Override
			public void cancel() {

			}
		});
	}

//	private Publisher<Integer> mapPub(Publisher<Integer> publisher, Function<Integer, Integer> function) {
//		return sub -> publisher.subscribe(new DelegateSub(sub) {
//			@Override
//			public void onNext(Integer integer) {
//				sub.onNext(function.apply(integer));
//			}
//		});
//	}

	private Subscriber<Integer> logSubscriber() {
		return new Subscriber<>() {
			@Override
			public void onSubscribe(Subscription s) {
				log.debug("Subscription");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer i) {
				log.debug("onNext={}", i);
			}

			@Override
			public void onError(Throwable t) {
				log.error(t.getMessage(), t);
			}

			@Override
			public void onComplete() {
				log.debug("onComplete");
			}
		};
	}
}
