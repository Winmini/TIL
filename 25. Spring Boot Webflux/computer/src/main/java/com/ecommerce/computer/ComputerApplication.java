package com.ecommerce.computer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.blockhound.BlockHound;

@SpringBootApplication
public class ComputerApplication {

	public static void main(String[] args) {
//		BlockHound.install();
		SpringApplication.run(ComputerApplication.class, args);
	}

}
