package com.amitshahi.retailforge.order.domain;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String newOrdersRoutingKey;

    public OrderEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${order.events.exchange}") String exchange,
            @Value("${order.events.new-orders-routing-key}") String newOrdersRoutingKey) {

        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.newOrdersRoutingKey = newOrdersRoutingKey;
    }

    public void publish(OrderEventEntity event) {

        rabbitTemplate.convertAndSend(exchange, newOrdersRoutingKey, event.getPayload());
    }
}
