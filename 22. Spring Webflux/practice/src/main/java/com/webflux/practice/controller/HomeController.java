package com.webflux.practice.controller;

import java.util.Observable;

import javax.validation.Valid;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.reactivestreams.client.FindPublisher;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

	@GetMapping
	public String home() {
		return "Hello Spring WebFlux";
	}


	//Flux<ServerSentEvent>, Observable<ServerSentEvent>
}
