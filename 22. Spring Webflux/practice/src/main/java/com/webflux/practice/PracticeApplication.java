package com.webflux.practice;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.webflux.practice.service.MyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class PracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class,args);
		// try (ConfigurableApplicationContext run = SpringApplication.run(PracticeApplication.class, args)) {
		// }
	}

	// @Autowired
	// MyService myService;
	//
	// @Bean
	// ApplicationRunner run() {
	// 	return args -> {
	// 		log.info("run()");
	// 		CompletableFuture<String> hello = myService.hello();
	// 		log.info("hello.isDone()={}", hello.isDone());
	// 		log.info("hello.get()={}", hello.get());
	// 	};
	// }
}
