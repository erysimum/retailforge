package com.amitshahi.retailforge.order.domain;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderProcessingJob {

    private final OrderRepository orderRepository;
    private final OrderProcessingService orderProcessingService;

    public OrderProcessingJob(OrderRepository orderRepository, OrderProcessingService orderProcessingService) {
        this.orderRepository = orderRepository;
        this.orderProcessingService = orderProcessingService;
    }

    @Scheduled(fixedDelayString = "${order.processing.fixed-delay}")
    public void processNewOrders() {
        List<OrderEntity> orders = orderRepository.findTop50ByStatusOrderByCreatedAtAsc(OrderStatus.NEW);

        for (OrderEntity order : orders) {
            try {
                orderProcessingService.process(order.getId());
            } catch (RuntimeException e) {
                log.error("Failed to process order {}", order.getOrderNumber(), e);
            }
        }
    }
}
