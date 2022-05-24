package com.ecommerce.computer.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class Cart {

	@Id
	private String id;
	private List<CartItem> cartItems = new ArrayList<>();


	@Builder
	public Cart(String id){
		this.id = id;
	}
}
