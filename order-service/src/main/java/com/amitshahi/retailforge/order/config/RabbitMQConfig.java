package com.amitshahi.retailforge.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RabbitMQConfig {
    private final String exchangeName;
    private final String newOrdersQueueName;
    private final String newOrdersRoutingKey;

    RabbitMQConfig(
            @Value("${order.events.exchange}") String exchangeName,
            @Value("${order.events.new-orders-queue}") String newOrdersQueueName,
            @Value("${order.events.new-orders-routing-key}") String newOrdersRoutingKey) {

        this.exchangeName = exchangeName;
        this.newOrdersQueueName = newOrdersQueueName;
        this.newOrdersRoutingKey = newOrdersRoutingKey;
    }

    @Bean
    DirectExchange orderExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Queue newOrdersQueue() {
        return QueueBuilder.durable(newOrdersQueueName).build();
    }

    @Bean
    Binding newOrdersBinding(Queue newOrdersQueue, DirectExchange orderExchange) {

        return BindingBuilder.bind(newOrdersQueue).to(orderExchange).with(newOrdersRoutingKey);
    }
}
