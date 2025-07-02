package com.acikgozkaan.stock_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    public RabbitMQConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(properties.exchange());
    }

    @Bean
    public Queue orderCreatedQueue() {
        return QueueBuilder.durable(properties.orderCreatedQueue())
                .withArgument("x-dead-letter-exchange", properties.deadLetterExchange())
                .withArgument("x-dead-letter-routing-key", properties.orderCreatedDlqRoutingKey())
                .build();
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder
                .bind(orderCreatedQueue())
                .to(orderExchange())
                .with(properties.orderCreatedRoutingKey());
    }

    // DLQ (Dead Letter Queue)
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(properties.deadLetterExchange());
    }

    @Bean
    public Queue orderCreatedDLQ() {
        return new Queue(properties.OrderCreatedDlqQueue(), true);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(orderCreatedDLQ())
                .to(deadLetterExchange())
                .with(properties.orderCreatedDlqRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}