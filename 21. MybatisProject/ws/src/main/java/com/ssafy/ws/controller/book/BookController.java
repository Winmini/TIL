package com.ssafy.ws.controller.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.controller.SuccessResponseResult;
import com.ssafy.ws.domain.Book;
import com.ssafy.ws.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping
	public SuccessResponseResult getBooksWithSwagger() {
		List<Book> books = bookService.findAll();
		return new SuccessResponseResult(new GetBooksResponse(books));
	}

	@GetMapping("{bookId}")
	public SuccessResponseResult getBook(@PathVariable int bookId) {
		Book book = bookService.findOne(bookId);
		return new SuccessResponseResult(new GetBookResponse(book));
	}

	@Data
	@AllArgsConstructor
	static class GetBooksResponse {
		List<Book> books;
	}

	@Data
	@AllArgsConstructor
	static class GetBookResponse {
		Book book;
	}
}
