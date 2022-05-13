package com.async.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteService {

	public static void main(String[] args) {
		System.setProperty("server.port", "8081");
		System.setProperty("server.tomcat.mat-threads", "100");
		SpringApplication.run(RemoteService.class, args);
	}

	@RestController
	public static class Controller {
		@GetMapping("/service")
		public String service(String req) throws InterruptedException {
			Thread.sleep(2000);
			return req + "/service";
		}
	}
}
