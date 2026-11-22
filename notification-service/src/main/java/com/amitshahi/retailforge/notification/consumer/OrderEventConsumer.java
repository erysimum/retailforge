package com.amitshahi.retailforge.notification.consumer;

import com.amitshahi.retailforge.notification.email.EmailMessage;
import com.amitshahi.retailforge.notification.email.EmailSender;
import com.amitshahi.retailforge.notification.event.OrderCreatedEvent;
import com.amitshahi.retailforge.notification.idempotency.IdempotentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderEventConsumer {

    private final JsonMapper jsonMapper;
    private final IdempotentProcessor idempotentProcessor;
    private final EmailSender emailSender;

    @RabbitListener(queues = "${order.events.notification-queue}")
    public void handleOrderCreated(String message) {

        OrderCreatedEvent event = jsonMapper.readValue(message, OrderCreatedEvent.class);

        log.info(
                "Received ORDER_CREATED event: orderNumber={}, customer={}, email={}, eventId={}",
                event.orderNumber(),
                event.customerName(),
                event.customerEmail(),
                event.eventId());

        idempotentProcessor.runOnce(
                event.eventId(),
                "ORDER_CREATED",
                () -> emailSender.send(new EmailMessage(
                        event.customerEmail(),
                        "Your RetailForge order " + event.orderNumber() + " is confirmed",
                        "Hi " + event.customerName() + ",\n\n"
                                + "Thanks for your order! "
                                + "Your order number is "
                                + event.orderNumber() + ".\n"
                                + "We'll let you know when it ships.\n\n"
                                + "— RetailForge",
                        event.eventId())));
    }
}
