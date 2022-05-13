package com.async.hello.controller;

import java.util.function.Consumer;

public class ErrorCompletion<T> extends Completion<T, T> {
	private Consumer<Throwable> consumer;

	public ErrorCompletion(Consumer<Throwable> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void error(Throwable e) {
		consumer.accept(e);
	}
}
