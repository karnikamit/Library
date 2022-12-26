package io.springworks.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.springworks.models.Ledger;
import io.springworks.utli.RabbitMQConstants;

@Component
public class LibraryProducer {

	private static Logger logger = LoggerFactory.getLogger(LibraryProducer.class);

	@Autowired
	@Qualifier("bookQueue")
	private Queue bookQueue;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void rentBook(Ledger record) {
		logger.info("Sending RabbitMQ message to rent a book.");
		rabbitTemplate.convertAndSend(RabbitMQConstants.BOOK_EXCHANGE_NAME, RabbitMQConstants.BOOK_ROUTING_KEY, record);
		
//		rabbitTemplate.sendAndReceive(record);
	}
}
