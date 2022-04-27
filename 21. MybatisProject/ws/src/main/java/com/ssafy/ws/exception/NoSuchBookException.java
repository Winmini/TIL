package com.ssafy.ws.exception;

import static com.ssafy.ws.exception.ExceptionMessage.*;

public class NoSuchBookException extends RuntimeException{
	public NoSuchBookException() {
		super(NO_SUCH_BOOK_MESSAGE);
	}

	public NoSuchBookException(String message) {
		super(message);
	}

	public NoSuchBookException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchBookException(Throwable cause) {
		super(cause);
	}
}
