package io.springworks.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.springworks.exceptions.LibraryExceptions;
import io.springworks.models.Book;
import io.springworks.models.Ledger;
import io.springworks.models.User;

@Service
public class LibraryService {

	private static Logger logger = LoggerFactory.getLogger(LibraryService.class);

	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

	@Autowired
	LedgerService ledgerService;

	@SuppressWarnings("deprecation")
	public void vaildateRecord(Ledger record, boolean validateBook, boolean validateUser) throws LibraryExceptions {
		if(record == null) {
			logger.error("Please provide valid document");
			throw new LibraryExceptions("Payload provided is not valid");
		}
		if(StringUtils.isEmpty(record.getBookName())
				|| StringUtils.isEmpty(record.getUserName())) {
			throw new LibraryExceptions("Invalid Book or User details");
		}
		if(record.getBookId() == 0
				|| record.getUserId() == 0) {
			throw new LibraryExceptions("Invalid User or Book ID");
		}
		if(validateBook) {
			validateBook(record);
		}
		if(validateUser) {
			validateUser(record);
		}
	}

	public void validateBook(Ledger record) throws LibraryExceptions {
		int bookId = record.getBookId();
		List<Book> books = bookService.getBook(bookId);
		if(CollectionUtils.isEmpty(books)) {
			throw new LibraryExceptions("Book not found with id: "+bookId);
		}
		Book book = books.get(0);
		if(!book.getName().equals(record.getBookName())) {
			throw new LibraryExceptions("Invalid book id");
		}
	}

	public void validateUser(Ledger record) throws LibraryExceptions{
		int userId = record.getUserId();
		List<User> users = userService.getUser(userId);
		if(CollectionUtils.isEmpty(users)) {
			throw new LibraryExceptions("User not found with Id "+userId);
		}
		User user = users.get(0);
		if(!user.getName().equals(record.getUserName())) {
			throw new LibraryExceptions("Invalid User");
		}
	}

	public int rentBook(Ledger record) {
		int ledgerId = -1;
		int bookId = bookService.rentBook(record.getBookId(), record.getUserId());
		if(bookId > 0) {
			ledgerId = ledgerService.addRecord(record);
		}
		return ledgerId;
	}
}
