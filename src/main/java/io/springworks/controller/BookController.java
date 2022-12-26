package io.springworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.springworks.models.Book;
import io.springworks.models.Response;
import io.springworks.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@GetMapping("/library")
	public long getBooksCount(){
		return bookService.getBooksCount();
	}

	// http://localhost:8080/api/v1/book/get?id=1
	@GetMapping("/get")
	public Response getBook(@RequestParam("id") int id) {
		Response response = new Response();
		logger.info("Requesting book with ID: {}", id);
		List<Book> books = bookService.getBook(id);
		if(CollectionUtils.isEmpty(books)) {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setErrorCode(HttpStatus.NOT_FOUND.value());
		}else {
			response.setIsSuccessful(Boolean.TRUE);
			response.setObjects(books);
			response.setHttpStatus(HttpStatus.FOUND);
		}
		return response;
	}

	@PostMapping("/add")
	public Response addBook(@RequestBody Book book){
		Response response = new Response();
		if(book == null) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setErrorCode(HttpStatus.BAD_REQUEST.value());
			return response;
		}
		response.setIsSuccessful(Boolean.TRUE);
		response.setHttpStatus(HttpStatus.CREATED);
		int bookId = bookService.addBook(book);
		List<Map<String, Integer>> bookIdList = new ArrayList<>();
		Map<String, Integer> bookIdMap = new HashMap<>();
		bookIdMap.put("BookId", bookId);
		bookIdList.add(bookIdMap);
		response.setObjects(bookIdList);
		logger.info("Successfully added book to library: Book - Autor:{}, Name: {}", book.getAuthor(), book.getName());
		return response;
	}

	@DeleteMapping("/delete")
	public Response deleteBook(@RequestParam("id") int id) {
		Response response = new Response();
		response.setIsSuccessful(Boolean.TRUE);
		response.setHttpStatus(HttpStatus.ACCEPTED);
		bookService.deleteBook(id);
		logger.info("Removing book with id - {} form library", id);
		return response;
	}
}
