package com.amitshahi.retailforge.notification.idempotency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdempotentProcessor {
    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    public boolean runOnce(String eventId, String eventType, Runnable action) {

        int inserted = processedEventRepository.tryInsert(eventId, eventType);

        // Security guard: Is this event already processed?
        if (inserted == 0) {
            log.info("duplicate, skipping eventId={}", eventId);
            return false;
        }

        // New event: execute the consumer's requested action.
        action.run();

        return true;
    }
}
