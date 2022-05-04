package com.webflux.practice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class HttpHandlerTest {

	@Test
	public void handlerTest() {
		HttpHandler httpHandler = (request, response) -> null;
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer.create().host("host").host("host").host("host").handle(adapter).bind().block();
	}
}
