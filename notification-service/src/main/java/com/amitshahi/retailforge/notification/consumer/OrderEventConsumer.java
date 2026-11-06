package com.amitshahi.retailforge.notification.consumer;

import com.amitshahi.retailforge.notification.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
public class OrderEventConsumer {

    private final JsonMapper jsonMapper;

    public OrderEventConsumer(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @RabbitListener(queues = "${order.events.notification-queue}")
    public void handleOrderCreated(String message) {

        OrderCreatedEvent event = jsonMapper.readValue(message, OrderCreatedEvent.class);

        log.info(
                "Received ORDER_CREATED event: orderNumber={}, customer={}, email={}, eventId={}",
                event.orderNumber(),
                event.customerName(),
                event.customerEmail(),
                event.eventId());
        log.info(
                "📧 Sending email to {}: Dear {}, your order {} has been placed!",
                event.customerEmail(),
                event.customerName(),
                event.orderNumber());
    }
}
