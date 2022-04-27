package com.ssafy.ws.service;

import java.util.List;

import com.ssafy.ws.domain.Book;

public interface BookService {

	Book findOne(int id);

	int save(Book book);

	List<Book> findAll();
}
