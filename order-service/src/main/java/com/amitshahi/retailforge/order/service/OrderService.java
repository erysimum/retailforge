package com.amitshahi.retailforge.order.service;

import com.amitshahi.retailforge.order.domain.OrderEntity;
import com.amitshahi.retailforge.order.domain.OrderMapper;
import com.amitshahi.retailforge.order.domain.OrderRepository;
import com.amitshahi.retailforge.order.domain.OrderValidator;
import com.amitshahi.retailforge.order.web.dto.CreateOrderRequest;
import com.amitshahi.retailforge.order.web.dto.CreateOrderResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public CreateOrderResponse createOrder(String username, CreateOrderRequest request) {
        orderValidator.validate(request);
        String orderNumber = generateOrderNumber();
        OrderEntity order = OrderMapper.toEntity(request, username, orderNumber);
        orderRepository.save(order);

        return new CreateOrderResponse(orderNumber);
    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
