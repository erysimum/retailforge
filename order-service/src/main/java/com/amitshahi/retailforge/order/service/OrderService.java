package com.amitshahi.retailforge.order.service;

import com.amitshahi.retailforge.order.domain.*;
import com.amitshahi.retailforge.order.web.dto.CreateOrderRequest;
import com.amitshahi.retailforge.order.web.dto.CreateOrderResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventRepository orderEventRepository;
    private final ObjectMapper objectMapper;

    public OrderService(
            OrderRepository orderRepository,
            OrderValidator orderValidator,
            OrderEventRepository orderEventRepository,
            ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventRepository = orderEventRepository;
        this.objectMapper = objectMapper;
    }

    public CreateOrderResponse createOrder(String username, CreateOrderRequest request) {
        String payloadJson;
        orderValidator.validate(request);
        String orderNumber = generateOrderNumber();
        OrderEntity order = OrderMapper.toEntity(request, username, orderNumber);
        orderRepository.save(order);

        OrderCreatedEvent event = buildOrderCreatedEvent(order);

        try {
            payloadJson = objectMapper.writeValueAsString(event);
        } catch (JacksonException e) {
            throw new RuntimeException("Failed to serialize order event", e);
        }
        OrderEventEntity outboxEvent = new OrderEventEntity(
                event.eventId(), OrderEventType.ORDER_CREATED, order.getOrderNumber(), payloadJson);
        orderEventRepository.save(outboxEvent);

        return new CreateOrderResponse(orderNumber);
    }

    private OrderCreatedEvent buildOrderCreatedEvent(OrderEntity order) {
        Set<OrderCreatedEvent.OrderEventItem> items = order.getItems().stream()
                .map(item -> new OrderCreatedEvent.OrderEventItem(
                        item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderCreatedEvent(
                UUID.randomUUID().toString(), // fresh eventId (the idempotency key)
                order.getOrderNumber(),
                items,
                order.getCustomer().name(), // from the Customer embeddable
                order.getCustomer().email(),
                LocalDateTime.now());
    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
