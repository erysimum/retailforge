package com.amitshahi.retailforge.notification.consumer;

import com.amitshahi.retailforge.notification.email.EmailMessage;
import com.amitshahi.retailforge.notification.email.EmailSender;
import com.amitshahi.retailforge.notification.event.OrderCancelledEvent;
import com.amitshahi.retailforge.notification.event.OrderCreatedEvent;
import com.amitshahi.retailforge.notification.event.OrderReadyToShipEvent;
import com.amitshahi.retailforge.notification.idempotency.IdempotentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
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
    public void onMessage(String message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        log.info("Received message with routing key {}", routingKey);
        switch (routingKey) {
            case OrderRoutingKeys.ORDER_CREATED -> handleOrderCreated(message);
            case OrderRoutingKeys.ORDER_READY_TO_SHIP -> handleOrderReadyToShip(message);
            case OrderRoutingKeys.ORDER_CANCELLED -> handleOrderCancelled(message);
            default -> throw new IllegalArgumentException("Unknown routing key: " + routingKey);
        }
    }

    private void handleOrderCreated(String message) {
        OrderCreatedEvent event = jsonMapper.readValue(message, OrderCreatedEvent.class);

        idempotentProcessor.runOnce(
                event.eventId(),
                "ORDER_CREATED",
                () -> emailSender.send(new EmailMessage(
                        event.customerEmail(),
                        "Your RetailForge order " + event.orderNumber() + " is confirmed",
                        "Hi " + event.customerName() + ",\n\n"
                                + "Thanks for your order! Your order number is " + event.orderNumber() + ".\n"
                                + "We'll let you know when it ships.\n\n"
                                + "— RetailForge",
                        event.eventId())));
    }

    private void handleOrderReadyToShip(String message) {
        OrderReadyToShipEvent event = jsonMapper.readValue(message, OrderReadyToShipEvent.class);

        idempotentProcessor.runOnce(
                event.eventId(),
                "ORDER_READY_TO_SHIP",
                () -> emailSender.send(new EmailMessage(
                        event.customerEmail(),
                        "Your RetailForge order " + event.orderNumber() + " is ready to ship",
                        "Hi " + event.customerName() + ",\n\n"
                                + "Good news! Your order " + event.orderNumber() + " has been packed "
                                + "and is ready to ship.\n"
                                + "We'll email you again when it's on its way.\n\n"
                                + "— RetailForge",
                        event.eventId())));
    }

    private void handleOrderCancelled(String message) {
        OrderCancelledEvent event = jsonMapper.readValue(message, OrderCancelledEvent.class);

        idempotentProcessor.runOnce(
                event.eventId(),
                "ORDER_CANCELLED",
                () -> emailSender.send(new EmailMessage(
                        event.customerEmail(),
                        "Your RetailForge order " + event.orderNumber() + " has been cancelled",
                        "Hi " + event.customerName() + ",\n\n"
                                + "Sorry, your order " + event.orderNumber() + " has been cancelled.\n"
                                + "Reason: " + describe(event.reason()) + "\n\n"
                                + "— RetailForge",
                        event.eventId())));
    }

    private static String describe(String reason) {
        return switch (reason) {
            case "DELIVERY_COUNTRY_NOT_SUPPORTED" -> "we don't deliver to your country yet.";
            default -> "please contact our support team.";
        };
    }
}
