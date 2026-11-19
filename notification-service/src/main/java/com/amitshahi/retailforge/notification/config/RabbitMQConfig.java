package com.amitshahi.retailforge.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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
    Queue notificationQueue(
            @Value("${order.events.notification-queue}") String queueName,
            @Value("${order.events.notification-dlx}") String dlxName,
            @Value("${order.events.notification-dlq-routing-key}") String dlqRoutingKey) {

        return QueueBuilder.durable(queueName)
                .deadLetterExchange(dlxName)
                .deadLetterRoutingKey(dlqRoutingKey)
                .build();
    }

    @Bean
    Binding notificationBinding(
            @Qualifier("notificationQueue") Queue notificationQueue,
            @Qualifier("orderExchange") DirectExchange orderExchange,
            @Value("${order.events.new-orders-routing-key}") String routingKey) {

        return BindingBuilder.bind(notificationQueue).to(orderExchange).with(routingKey);
    }

    // Dead-letter exchange
    @Bean
    DirectExchange notificationDeadLetterExchange(@Value("${order.events.notification-dlx}") String dlxName) {

        return new DirectExchange(dlxName, true, false);
    }

    // Dead-letter queue
    @Bean
    Queue notificationDeadLetterQueue(@Value("${order.events.notification-dlq}") String dlqName) {

        return QueueBuilder.durable(dlqName).build();
    }

    // Dead-letter exchange -> dead-letter queue
    @Bean
    Binding notificationDeadLetterBinding(
            @Qualifier("notificationDeadLetterQueue") Queue dlq,
            @Qualifier("notificationDeadLetterExchange") DirectExchange dlx,
            @Value("${order.events.notification-dlq-routing-key}") String routingKey) {

        return BindingBuilder.bind(dlq).to(dlx).with(routingKey);
    }
}
