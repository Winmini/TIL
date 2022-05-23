package com.ecommerce.computer.domain;

import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Item {
	@Id
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	private String name;
	private double price;

	// private Item(Builder builder){
	// 	name = builder.name;
	// 	price = builder.price;
	// }
	//
	// static public class Builder {
	// 	private String name;
	// 	private double price;
	//
	// 	public Builder name(String name){
	// 		this.name = name;
	// 		return this;
	// 	}
	//
	// 	public Builder price(double price){
	// 		this.price = price;
	// 		return this;
	// 	}
	//
	// 	public Item build(){
	// 		return new Item(this);
	// 	}
	// }
}
