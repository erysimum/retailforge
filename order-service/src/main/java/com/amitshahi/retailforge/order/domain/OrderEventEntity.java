package com.amitshahi.retailforge.order.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_events")
public class OrderEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_event_id_generator")
    @SequenceGenerator(name = "order_event_id_generator", sequenceName = "order_event_id_seq", allocationSize = 50)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderEventType eventType;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false, columnDefinition = "text")
    private String payload;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected OrderEventEntity() {
        // Required by JPA
    }

    public OrderEventEntity(String eventId, OrderEventType eventType, String orderNumber, String payload) {

        this.eventId = Objects.requireNonNull(eventId);
        this.eventType = Objects.requireNonNull(eventType);
        this.orderNumber = Objects.requireNonNull(orderNumber);
        this.payload = Objects.requireNonNull(payload);
        this.createdAt = LocalDateTime.now();
    }

    public String getEventId() {
        return eventId;
    }

    public Long getId() {
        return id;
    }

    public OrderEventType getEventType() {
        return eventType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPayload() {
        return payload;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
