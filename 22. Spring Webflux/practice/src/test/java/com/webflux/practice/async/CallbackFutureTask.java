package com.webflux.practice.async;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.springframework.util.concurrent.SuccessCallback;

public class CallbackFutureTask extends FutureTask<String > {
	SuccessCallback<String> sc;
	ExceptionCallback ec;
	public CallbackFutureTask(Callable<String> callable, SuccessCallback<String> sc,
							  ExceptionCallback ec) {
		super(callable);
		this.sc = Objects.requireNonNull(sc);
		this.ec = ec;
	}

	@Override
	protected void done() {
		try {
			sc.onSuccess(get());
		} catch (InterruptedException e){
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			ec.onError(e.getCause());
		}
	}
}
