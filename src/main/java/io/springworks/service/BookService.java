package io.springworks.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import io.springworks.models.Book;
import io.springworks.repos.BookRepo;

@Service
public class BookService {

	private static Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepo bookRepo;

	private List<Book> books;

	public long getBooksCount(){
		long count = bookRepo.count();
		logger.info("Found {} books", count);
		return count;

	}

	public List<Book> getbook(int id) {
		List<Book> books = new ArrayList<>();
		Optional<Book> book = bookRepo.findById(id);
		if(book.isPresent()) {
			logger.info("Found book with ID: {}", id);
			books.add(book.get());
		}
		return books;
	}

	public void addBook(Book book) {
		logger.info("Adding book by Author: {} Book name - {} to library", book.getAuthor(), book.getName());
		bookRepo.save(book);
	}

	public void deleteBook(int id) {
		List<Book> books = getbook(id);
		if(!CollectionUtils.isEmpty(books)){
			Book book = books.get(0);
			bookRepo.delete(book);
			logger.info("Deleting book by Author: {} Book name - {} from inventory", book.getAuthor(), book.getName());
			return;
		}
		logger.error("Could not find the book by ID: {}", id);
	}
}
