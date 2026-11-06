package com.amitshahi.retailforge.notification.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        String eventId,
        String orderNumber,
        List<OrderEventItem> items,
        String customerName,
        String customerEmail,
        LocalDateTime createdAt) {

    public record OrderEventItem(String code, String name, BigDecimal price, Integer quantity) {}
}
