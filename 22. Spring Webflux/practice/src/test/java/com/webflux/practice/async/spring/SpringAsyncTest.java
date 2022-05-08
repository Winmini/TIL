package com.webflux.practice.async.spring;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootTest
public class SpringAsyncTest {

	Logger log = LoggerFactory.getLogger(SpringAsyncTest.class);

	@Test
	void start(String[] args) {
		try (ConfigurableApplicationContext run = SpringApplication.run(SpringAsyncTest.class, args)) {
		}
	}

	@Autowired
	MyService myService;

	@Bean
	ApplicationRunner run() {
		return args -> {
			log.info("run()");
		};
	}
}
