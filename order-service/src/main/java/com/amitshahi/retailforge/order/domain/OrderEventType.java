package com.amitshahi.retailforge.order.domain;

public enum OrderEventType {
    ORDER_CREATED("order.created"),
    ORDER_READY_TO_SHIP("order.ready-to-ship"),
    ORDER_CANCELLED("order.cancelled");

    private final String routingKey;

    OrderEventType(String routingKey) {
        this.routingKey = routingKey;
    }

    public String routingKey() {
        return routingKey;
    }
}
