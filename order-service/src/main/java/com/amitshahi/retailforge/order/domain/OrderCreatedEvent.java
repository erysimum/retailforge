package com.amitshahi.retailforge.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderCreatedEvent(
        String eventId,
        String orderNumber,
        Set<OrderEventItem> items,
        String customerName,
        String customerEmail,
        LocalDateTime createdAt) {

    public record OrderEventItem(String code, String name, BigDecimal price, Integer quantity) {}
}
