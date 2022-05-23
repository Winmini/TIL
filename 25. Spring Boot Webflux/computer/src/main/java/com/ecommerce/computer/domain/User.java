package com.ecommerce.computer.domain;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String id;
	private String password;
}
