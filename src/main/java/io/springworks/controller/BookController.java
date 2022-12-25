package io.springworks.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public long getBooksCount(){
		return bookService.getBooksCount();
	}

	// http://localhost:8080/api/v1/book/get?id=1
	@GetMapping("/get")
	public List<Book> getBook(@RequestParam("id") int id) {
		logger.info("Requesting book with ID: {}", id);
		List<Book> books = bookService.getbook(id);
		return books;
	}

	@PostMapping("/add")
	public HttpStatus addBook(@RequestBody Book book){
		bookId+=1;
		book.setId(bookId);
		bookService.addBook(book);
		logger.info("Successfully added book to library: Book - Autor:{}, Name: {}", book.getAuthor(), book.getName());
		return HttpStatus.ACCEPTED;
	}

	@DeleteMapping("/delete")
	public HttpStatus deleteBook(@RequestParam("id") int id) {
		bookService.deleteBook(id);
		logger.info("Removing book with id - {} form library", id);
		return HttpStatus.OK;
	}
}
