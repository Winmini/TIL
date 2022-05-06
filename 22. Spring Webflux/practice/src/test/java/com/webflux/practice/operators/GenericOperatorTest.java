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

public class GenericOperatorTest {

	Logger log = (Logger)LoggerFactory.getLogger(GenericOperatorTest.class);

	@Test
	void basic() {
		Iterable<Integer> iter = Stream.iterate(1, a -> a + 1).limit(10).collect(toList());

		Publisher<Integer> publisher = getPublisher(iter);
		Publisher<Integer> mapPub = mapPub(publisher, s -> s * 10);

		Publisher<StringBuilder> reducePub = reducePub(mapPub, new StringBuilder(), StringBuilder::append);

		reducePub.subscribe(logSubscriber());

	}

	//	private <T> Publisher<T> mapPub(Publisher<T> pub, Function<T, T> f) {
	//		return sub -> pub.subscribe(new DelegateSub<T>(sub) {
	//			@Override
	//			public void onNext(T t) {
	//				sub.onNext(f.apply(t));
	//			}
	//		});
	//	}

	private <T, R> Publisher<R> mapPub(Publisher<T> pub, Function<T, R> f) {
		return sub -> pub.subscribe(new DelegateSub<T, R>(sub) {
			@Override
			public void onNext(T t) {
				sub.onNext(f.apply(t));
			}
		});
	}

	private <T, R> Publisher<R> reducePub(Publisher<T> pub, R init,  BiFunction<R, T, R> function) {
		return sub -> pub.subscribe(new DelegateSub<T, R>(sub) {
			R result = init;

			@Override
			public void onNext(T t) {
				result = function.apply(result, t);
			}

			@Override
			public void onComplete() {
				sub.onNext(result);
				sub.onComplete();
			}
		});
	}

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

	private <T>  Subscriber<T> logSubscriber() {
		return new Subscriber<T>() {
			@Override
			public void onSubscribe(Subscription s) {
				log.debug("Subscription");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(T t) {
				log.debug("onNext={}", t);
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
