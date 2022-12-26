package io.springworks;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.springworks.utli.RabbitMQConstants;

@Configuration
public class RabbitMQConfig {

	@Bean
	public TopicExchange userTopicExchange() {
		return new TopicExchange(RabbitMQConstants.USER_EXCHANGE_NAME);
	}

	@Bean
	public Queue userQueue() {
		return new Queue(RabbitMQConstants.USER_QUEUE_NAME);
	}

	@Bean
	public Binding userQueueToExchangeBinding() {
		return BindingBuilder.bind(userQueue()).to(userTopicExchange()).with(RabbitMQConstants.USER_ROUTING_KEY);
	}

	@Bean
	public TopicExchange bookTopicExchange() {
		return new TopicExchange(RabbitMQConstants.BOOK_EXCHANGE_NAME);
	}

	@Bean
	public Queue bookQueue() {
		return new Queue(RabbitMQConstants.BOOK_QUEUE_NAME);
	}

	@Bean
	public Binding bookQueueToExchangeBinding() {
		return BindingBuilder.bind(bookQueue()).to(bookTopicExchange()).with(RabbitMQConstants.BOOK_ROUTING_KEY);
	}

	@Bean
	public TopicExchange ledgerTopicExchange() {
		return new TopicExchange(RabbitMQConstants.LEDGER_EXCHANGE_NAME);
	}

	@Bean
	public Queue ledgerQueue() {
		return new Queue(RabbitMQConstants.LEDGER_QUEUE_NAME);
	}

	@Bean
	public Binding ledgerToExchangeBinding() {
		return BindingBuilder.bind(ledgerQueue()).to(ledgerTopicExchange()).with(RabbitMQConstants.LEDGER_ROUTING_KEY);
	}
}
