package com.async.hello.controller;

import java.util.function.Consumer;

public class AcceptCompletion<S> extends Completion<S, Void>{
	private final Consumer<S> consumer;

	public AcceptCompletion(Consumer<S> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void run(S value){
		consumer.accept(value);
	}
}
