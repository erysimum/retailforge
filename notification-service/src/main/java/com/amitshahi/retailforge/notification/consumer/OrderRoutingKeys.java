package com.amitshahi.retailforge.notification.consumer;

/**
 * Routing keys published by order-service.
 * Must match order-service's OrderEventType.
 */
public final class OrderRoutingKeys {

    public static final String ORDER_CREATED = "order.created";

    public static final String ORDER_READY_TO_SHIP = "order.ready-to-ship";

    public static final String ORDER_CANCELLED = "order.cancelled";

    private OrderRoutingKeys() {}
}
