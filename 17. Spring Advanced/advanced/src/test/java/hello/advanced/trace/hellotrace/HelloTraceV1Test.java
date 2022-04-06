package hello.advanced.trace.hellotrace;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.advanced.trace.TraceStatus;

class HelloTraceV1Test {

	@Test
	@DisplayName("정상흐름")
	public void normal() throws Exception{
		HelloTraceV1 trace = new HelloTraceV1();
		TraceStatus status = trace.begin("hello");
		trace.end(status);
	}

	@Test
	@DisplayName("예외흐름")
	public void exception() throws Exception{
		HelloTraceV1 trace = new HelloTraceV1();
		TraceStatus status = trace.begin("hello");
		trace.exception(status, new IllegalArgumentException());
	}

}