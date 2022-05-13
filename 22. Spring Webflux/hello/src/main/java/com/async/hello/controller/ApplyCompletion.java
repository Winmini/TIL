package com.async.hello.controller;

import java.util.function.Function;

import org.springframework.util.concurrent.ListenableFuture;

public class ApplyCompletion<S, T> extends Completion<S, T>{

	private final Function<S, ListenableFuture<T>> function;

	public ApplyCompletion(Function<S, ListenableFuture<T>> function) {
		this.function = function;
	}

	void process(S value) {
		ListenableFuture<T> lf = function.apply(value);
		lf.addCallback(this::complete, this::error);
	}
}
