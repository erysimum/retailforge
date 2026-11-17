package com.amitshahi.retailforge.notification.idempotency;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
public class ProcessedEventRepositoryTest {

    @Autowired
    private ProcessedEventRepository processedEventRepository;

    @Test
    void tryInsert_claimsNewEvent_thenRejectsDuplicate() {
        int firstInsert = processedEventRepository.tryInsert("evt-1", "ORDER_CREATED");

        int secondInsert = processedEventRepository.tryInsert("evt-1", "ORDER_CREATED");

        assertThat(firstInsert).isEqualTo(1);
        assertThat(secondInsert).isEqualTo(0);
    }
}
