package com.amitshahi.retailforge.order.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderEventServiceProcessor {
    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderEventServiceProcessor(
            OrderEventRepository orderEventRepository, OrderEventPublisher orderEventPublisher) {
        this.orderEventRepository = orderEventRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    public void publishEvents() {

        List<OrderEventEntity> events = orderEventRepository.findAllByOrderByCreatedAtAsc();

        for (OrderEventEntity event : events) {
            orderEventPublisher.publish(event);
            orderEventRepository.delete(event);
        }
    }
}
