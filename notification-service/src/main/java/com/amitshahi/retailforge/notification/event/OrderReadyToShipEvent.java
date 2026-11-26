package com.amitshahi.retailforge.notification.event;

import java.time.LocalDateTime;

public record OrderReadyToShipEvent(
        String eventId, String orderNumber, String customerName, String customerEmail, LocalDateTime createdAt) {}
