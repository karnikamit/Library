package io.springworks.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.springworks.models.Book;
import io.springworks.repos.BookRepo;

@Service
public class BookService {

	private static Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepo bookRepo;

	private List<Book> books;

	public List<Book> getBooks(){
		books = new ArrayList<>();
		bookRepo.findAll()
		.forEach(books::add);
		logger.info("Found {} books", books.size());
		return books;
	}

	public Book getbook(int id) {
		Object book = bookRepo.findById(id);
		if(book != null) {
			logger.info("Found book with ID: {}", id);
			return (Book) book;
		}
		return null;
	}

	public void addBook(Book book) {
		logger.info("Adding book by Author: {} Book name - {} to library", book.getAuthor(), book.getName());
		bookRepo.save(book);
	}

	public void deleteBook(Book book) {
		logger.info("Deleting book by Author: {} Book name - {} from inventory", book.getAuthor(), book.getName());
		bookRepo.delete(book);
	}
}
