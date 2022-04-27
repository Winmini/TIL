package com.ssafy.ws.domain;

import lombok.Data;

@Data
public class Book {
	private int id;
	private String isbn;
	private String title;
	private String author;
	private int price;
	private String content;
	private String img;
	private String orgImg;
}
