package com.amitshahi.retailforge.notification.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, String> {
    @Modifying
    @Query(value = """
                    INSERT INTO processed_events (event_id, event_type)
                    VALUES (:eventId, :eventType)
                    ON CONFLICT (event_id) DO NOTHING
                    """, nativeQuery = true)
    int tryInsert(@Param("eventId") String eventId, @Param("eventType") String eventType);
}
