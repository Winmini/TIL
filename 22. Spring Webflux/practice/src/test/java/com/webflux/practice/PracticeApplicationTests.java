package com.webflux.practice;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscription;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class PracticeApplicationTests {

	@Test
	void publisher() {
		Iterable<Integer> iter = List.of(1, 2, 3, 4, 5);

		Publisher p = subscriber -> {
			Iterator<Integer> it = iter.iterator();
			subscriber.onSubscribe(new Subscription() {
				@Override
				public void request(long n) {
					int i = 0;
					while (i++ < n) {
						if (it.hasNext()) {
							subscriber.onNext(it.next());
						} else {
							subscriber.onComplete();
							break;
						}
					}
				}

				@Override
				public void cancel() {

				}
			});
		};

		Flow.Subscriber<Integer> s = new Flow.Subscriber<>() {
			Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("PracticeApplicationTests.onSubscribe");
				this.subscription = subscription;
				subscription.request(1);
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("PracticeApplicationTests.onNext " + item);
				subscription.request(1);
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("PracticeApplicationTests.onError");
			}

			@Override
			public void onComplete() {
				System.out.println("PracticeApplicationTests.onComplete");
			}
		};

		p.subscribe(s);
	}

	@Test
	void observable() {
		Observer ob = (o, arg) -> System.out.println(Thread.currentThread().getName() + " " + arg);
		IntObservable io = new IntObservable();
		io.addObserver(ob);

		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(io);

		System.out.println(Thread.currentThread().getName() + " EXIT");
		executor.shutdown();
	}

	// Iterable <--> Observable
	// Pull
	@Test
	void iterable() {
		Iterable<Integer> iter = List.of(1, 2, 3, 4, 5);
		Iterable<Integer> iterators = () -> new Iterator<>() {
			final static int MAX = 10;
			int i = 0;

			public boolean hasNext() {
				return i < MAX;
			}

			public Integer next() {
				return ++i;
			}
		};

		for (Integer i : iterators) {
			System.out.println(i);
		}

		for (Iterator<Integer> it = iterators.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}

	@Test
	void contextLoads() {
		Flux<String> flux = Flux.empty();
		Flux<String> just = Flux.just("foo", "bar");
		List<String> list = new ArrayList<>();
		list.add("foo");
		list.add("bar");
		Flux<String> stringFlux = Flux.fromIterable(list);
		Flux<String> stringFlux1 = Flux.fromIterable(List.of("foo", "bar"));
		stringFlux.doOnNext(System.out::println).map(String::toUpperCase).subscribe(System.out::println);

	}

	@Test
	void fluxTest() throws InterruptedException {
		Flux.interval(Duration.ofMillis(100)).take(10).subscribe(System.out::println);

		System.out.println("어느거먼저");
		Thread.sleep(1000);
	}

	@Test
	void monoTest() {
		Mono.just(1).map(integer -> 2 * integer).or(Mono.just(100)).subscribe(System.out::println);
	}

	static class IntObservable extends Observable implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				setChanged();
				notifyObservers(i);
			}
		}
	}

}
