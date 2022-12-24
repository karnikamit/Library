package io.springworks.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.springworks.models.Book;
import io.springworks.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	private int bookId = 0000;

	@GetMapping("/library")
	public List<Book> getBooks(){
		return bookService.getBooks();
	}

	// http://localhost:8080/api/v1/book/get?id=1
	@GetMapping("/get")
	public List<Book> getBook(@RequestParam("id") int id) {
		logger.info("Requesting book with ID: {}", id);
		List<Book> books = new ArrayList<Book>();
		Book book = bookService.getbook(id);
		if(book != null) {
			logger.info("Returning book with ID: {}", id);
			books.add(book);
			return books;
		}
		return books;
	}

	@PostMapping("/add")
	public BodyBuilder addBook(@RequestBody Book book){
		bookId+=1;
		book.setId(bookId);
		bookService.addBook(book);
		logger.info("Successfully added book to library: Book - Autor:{}, Name: {}", book.getAuthor(), book.getName());
		return ResponseEntity.accepted();
	}

	@DeleteMapping
	public BodyBuilder deleteBook(Book book) {
		bookService.deleteBook(book);
		logger.info("Removed book - {} form library", book.getName());
		return ResponseEntity.accepted();
	}
}
