package com.amitshahi.retailforge.order.domain;

import java.time.LocalDateTime;

public record OrderCancelledEvent(
        String eventId,
        String orderNumber,
        String customerName,
        String customerEmail,
        String reason,
        LocalDateTime createdAt) {}
