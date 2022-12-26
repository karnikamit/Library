package io.springworks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import io.springworks.controller.ControllerConstants;
import io.springworks.models.Book;
import io.springworks.repos.BookRepo;

@Service
public class BookService {

	private static Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepo bookRepo;

//	@Autowired
//	private RentBookRepo rentBookRepo;

	public long getBooksCount() {
		long count = bookRepo.count();
		logger.info("Found {} books", count);
		return count;

	}

	public List<Book> getBook(int id) {
		List<Book> books = new ArrayList<>();
		Optional<Book> book = bookRepo.findById(id);
		if (book.isPresent()) {
			logger.info("Found book with ID: {}", id);
			books.add(book.get());
		}
		return books;
	}

	/**
	 * Method to add book to library
	 * 
	 * @param book
	 */
	public int addBook(Book book) {
		if (book.getId() == 0) {
			ControllerConstants.bookId += 1;
			book.setId(ControllerConstants.bookId);
		}
		logger.info("Adding book by Author: {} Book name - {} Book ID - {} to library", book.getAuthor(),
				book.getName(), book.getId());
		bookRepo.save(book);
		return book.getId();
	}

	public void deleteBook(int id) {
		List<Book> books = getBook(id);
		if (!CollectionUtils.isEmpty(books)) {
			Book book = books.get(0);
			bookRepo.delete(book);
			logger.info("Deleting book by Author: {} Book name - {} from inventory", book.getAuthor(), book.getName());
			return;
		}
		logger.error("Could not find the book by ID: {}", id);
	}

	public int rentBook(int id, int userId) {
		if(id == 0 || userId == 0) {
			logger.error("Invalid Book ID or User ID");
			return -1;
		}
		List<Book> books = getBook(id);
		if(CollectionUtils.isEmpty(books) || books.get(0) == null) {
			logger.error("Book NOT found");
			return -1;
		}
		Book book = books.get(0);
		if(book.getRentingUserId() != 0) {
			logger.error("Book is already borrowed by the user ", book.getRentingUserId());
			return -1;
		}
		book.setRentingUserId(userId);
		return addBook(book);
	}

	public void returnBook(int id) {
		if(id == 0) {
			logger.error("Invalid book id");
		}
		List<Book> books = getBook(id);
		if(CollectionUtils.isEmpty(books) || books.get(0) == null) {
			logger.error("Book NOT found");
			return;
		}
		Book book = books.get(0);
		if(book.getRentingUserId() == 0) {
			logger.error("Invalid book {}", id);
			return;
		}
		book.setRentingUserId(0);
		addBook(book);
	}
}
