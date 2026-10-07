package com.amitshahi.retailforge.order.service;

import com.amitshahi.retailforge.order.domain.OrderEntity;
import com.amitshahi.retailforge.order.domain.OrderMapper;
import com.amitshahi.retailforge.order.domain.OrderRepository;
import com.amitshahi.retailforge.order.web.dto.CreateOrderRequest;
import com.amitshahi.retailforge.order.web.dto.CreateOrderResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(String username, CreateOrderRequest request) {
        String orderNumber = generateOrderNumber();
        OrderEntity order= OrderMapper.toEntity(request, username, orderNumber);
        orderRepository.save(order);

        return new CreateOrderResponse(orderNumber);

    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
