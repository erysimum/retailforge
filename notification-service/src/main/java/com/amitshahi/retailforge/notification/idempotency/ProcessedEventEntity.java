package com.amitshahi.retailforge.notification.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "processed_events")
class ProcessedEventEntity {
    @Id
    @Column(name = "event_id", nullable = false, updatable = false)
    private String eventId;

    @Column(name = "event_type", nullable = false, updatable = false)
    private String eventType;

    @Column(name = "processed_at", nullable = false, insertable = false, updatable = false)
    private Instant processedAt;

    protected ProcessedEventEntity() {}

    ProcessedEventEntity(String eventId, String eventType) {
        this.eventId = Objects.requireNonNull(eventId, "eventId must not be null");
        this.eventType = Objects.requireNonNull(eventType, "eventType must not be null");
    }

    String getEventId() {
        return eventId;
    }

    String getEventType() {
        return eventType;
    }

    Instant getProcessedAt() {
        return processedAt;
    }
}
