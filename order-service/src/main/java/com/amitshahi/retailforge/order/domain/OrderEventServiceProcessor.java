package com.amitshahi.retailforge.order.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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

        List<OrderEventEntity> events =
                orderEventRepository.findByPublicationStatusOrderByCreatedAtAsc(PublicationStatus.PENDING);

        for (OrderEventEntity event : events) {
            try {
                orderEventPublisher.publish(event);
                event.markAsPublished();
                // orderEventRepository.save(event);

            } catch (Exception e) {
                log.error("Failed to publish event {}: {}", event.getEventId(), e.getMessage(), e);
            }
        }
    }
}
