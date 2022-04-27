package com.ssafy.ws.controller.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.controller.SuccessResponseResult;
import com.ssafy.ws.domain.Book;
import com.ssafy.ws.service.BookService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping
	public SuccessResponseResult getBooks() {
		List<Book> books = bookService.findAll();
		return new SuccessResponseResult(new GetBooksResponse(books));
	}

	@Data
	@AllArgsConstructor
	static class GetBooksResponse {
		List<Book> books;
	}
}
