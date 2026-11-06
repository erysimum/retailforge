package com.amitshahi.retailforge.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    DirectExchange orderExchange(@Value("${order.events.exchange}") String exchangeName) {

        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Queue notificationQueue(@Value("${order.events.notification-queue}") String queueName) {

        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    Binding notificationBinding(
            Queue notificationQueue,
            DirectExchange orderExchange,
            @Value("${order.events.new-orders-routing-key}") String routingKey) {

        return BindingBuilder.bind(notificationQueue).to(orderExchange).with(routingKey);
    }
}
