package com.amitshahi.retailforge.order.domain;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
        CorrelationData correlationData = new CorrelationData(event.getEventId());

        rabbitTemplate.convertAndSend(exchange, newOrdersRoutingKey, event.getPayload(), correlationData);

        try {
            CorrelationData.Confirm confirm = correlationData.getFuture().get(5, TimeUnit.SECONDS);

            if (!confirm.ack()) {

                throw new IllegalStateException(
                        "RabbitMQ did not ACK event: " + event.getEventId() + ", reason: " + confirm.reason());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for RabbitMQ confirmation", e);

        } catch (ExecutionException | TimeoutException e) {
            throw new IllegalStateException("Failed to confirm publish", e);
        }
    }
}
