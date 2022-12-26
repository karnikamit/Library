package io.springworks.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.springworks.models.Ledger;
import io.springworks.service.BookService;
import io.springworks.utli.RabbitMQConstants;

@Component
public class BookConsumer {

	private static Logger logger = LoggerFactory.getLogger(BookConsumer.class);

	@Autowired
	BookService bookService;

	@RabbitListener(queues = RabbitMQConstants.BOOK_QUEUE_NAME)
	public void receiveMessage(Ledger ledger) {
		logger.info("received message: {}", ledger);
		bookService.rentBook(ledger.getBookId(), ledger.getUserId());
//		if(bookId > 0) {	//TODO
//			lp.updateLedger(ledger);
//		}
	}
}
