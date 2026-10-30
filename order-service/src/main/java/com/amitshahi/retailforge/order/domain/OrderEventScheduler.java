package com.amitshahi.retailforge.order.domain;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderEventScheduler {
    private final OrderEventServiceProcessor orderEventServiceProcessor;

    public OrderEventScheduler(OrderEventServiceProcessor orderEventServiceProcessor) {
        this.orderEventServiceProcessor = orderEventServiceProcessor;
    }

    @Scheduled(cron = "${order.events.publish-job-cron}")
    public void publishPendingEvents() {
        orderEventServiceProcessor.publishEvents();
    }
}
