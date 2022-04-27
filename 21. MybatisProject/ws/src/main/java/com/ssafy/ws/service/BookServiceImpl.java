package com.ssafy.ws.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.ws.domain.Book;
import com.ssafy.ws.exception.NoSuchBookException;
import com.ssafy.ws.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

	private final BookMapper bookMapper;

	@Override
	public Book findOne(int id) {
		return bookMapper.findById(id).orElseThrow(NoSuchBookException::new);
	}

	@Override
	public int save(Book book) {
		return bookMapper.save(book);
	}

	@Override
	public List<Book> findAll() {
		return bookMapper.findAll();
	}
}
