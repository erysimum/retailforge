package com.amitshahi.retailforge.order.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {
    List<OrderEventEntity> findAllByOrderByCreatedAtAsc();
}
