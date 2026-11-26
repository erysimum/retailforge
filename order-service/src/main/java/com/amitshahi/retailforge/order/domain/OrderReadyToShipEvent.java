package com.amitshahi.retailforge.order.domain;

import java.time.LocalDateTime;

public record OrderReadyToShipEvent(
        String eventId, String orderNumber, String customerName, String customerEmail, LocalDateTime createdAt) {}
