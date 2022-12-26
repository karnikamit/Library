package io.springworks.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.springworks.models.Ledger;
import io.springworks.service.LedgerService;
import io.springworks.utli.RabbitMQConstants;

@Component
public class LedgerConsumer {

	private static Logger logger = LoggerFactory.getLogger(LedgerConsumer.class);

	@Autowired
	private LedgerService ledgerService;

	@RabbitListener(queues = RabbitMQConstants.LEDGER_QUEUE_NAME)
	public void receiveMessage(Ledger record) {
		logger.info("Received message to update Ledger: {}", record);
		ledgerService.addRecord(record);
	}
}
