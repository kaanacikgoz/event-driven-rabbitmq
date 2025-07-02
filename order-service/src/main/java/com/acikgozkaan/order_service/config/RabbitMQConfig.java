package com.acikgozkaan.order_service.config;

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
    public Queue stockRejectedQueue() {
        return new Queue(properties.stockRejectedQueue(), true);
    }

    @Bean
    public Queue paymentConfirmedQueue() {
        return new Queue(properties.paymentConfirmedQueue(), true);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(properties.paymentFailedQueue(), true);
    }

    @Bean
    public Binding stockRejectedBinding() {
        return BindingBuilder
                .bind(stockRejectedQueue())
                .to(orderExchange())
                .with(properties.stockRejectedRoutingKey());
    }

    @Bean
    public Binding paymentConfirmedBinding() {
        return BindingBuilder.bind(paymentConfirmedQueue())
                .to(orderExchange())
                .with(properties.paymentConfirmedRoutingKey());
    }

    @Bean
    public Binding paymentFailedBinding() {
        return BindingBuilder.bind(paymentFailedQueue())
                .to(orderExchange())
                .with(properties.paymentFailedRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}