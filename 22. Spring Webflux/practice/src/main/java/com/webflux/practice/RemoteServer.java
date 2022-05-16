package com.webflux.practice;

import java.util.concurrent.CompletableFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteServer {

	public static void main(String[] args) {
		System.setProperty("server.port", "8081");
		SpringApplication.run(RemoteServer.class, args);
	}

	@RestController
	@EnableAsync
	public static class Controller {
		@Async
		@GetMapping("/service")
		public CompletableFuture<String> service(String req) throws InterruptedException {
			Thread.sleep(1000);
			return CompletableFuture.completedFuture(req + "/service");
		}
	}
}