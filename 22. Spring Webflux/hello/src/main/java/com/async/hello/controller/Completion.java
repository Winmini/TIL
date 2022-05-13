package com.async.hello.controller;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.util.concurrent.ListenableFuture;

public class Completion<S, T> {
	private Completion next;

	public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
		Completion<S, T> c = new Completion<>();
		lf.addCallback(c::complete, c::error);
		return c;
	}

	public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> func) {
		Completion<T, V> c = new ApplyCompletion<>(func);
		this.next = c;
		return c;
	}

	public Completion<T, T> andError(Consumer<Throwable> throwable) {
		Completion<T, T> c = new ErrorCompletion<>(throwable);
		this.next = c;
		return c;
	}

	public void andAccept(Consumer<T> con) {
		Completion<T, Void> com = new AcceptCompletion<>(con);
		this.next = com;
	}

	public void error(Throwable e) {
		if (next != null) {
			next.error(e);
		}
	}

	public void run(S value) {
		if (next != null) {
			next.run(value);
		}
	}

	public void complete(T s) {
		if (next != null) {
			next.run(s);
		}
	}

}
