package com.amitshahi.retailforge.order.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findTop50ByStatusOrderByCreatedAtAsc(OrderStatus status);
}
