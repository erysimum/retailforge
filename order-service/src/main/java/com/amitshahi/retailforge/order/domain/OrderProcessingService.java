package com.amitshahi.retailforge.order.domain;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final Set<String> supportedCountries;

    public OrderProcessingService(
            OrderRepository orderRepository,
            @Value("${order.processing.supported-countries}") String[] supportedCountries) {

        this.orderRepository = orderRepository;
        this.supportedCountries = Arrays.stream(supportedCountries)
                .map(OrderProcessingService::normalise)
                .collect(Collectors.toUnmodifiableSet());

        if (this.supportedCountries.isEmpty()) {
            throw new IllegalStateException("order.processing.supported-countries must not be empty");
        }
    }

    @Transactional
    public void process(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow();

        if (order.getStatus() != OrderStatus.NEW) {
            return; // already handled
        }

        String country = order.getDeliveryAddress().country();

        if (supportedCountries.contains(normalise(country))) {
            order.markReadyToShip();
            log.info("Order {} -> READY_TO_SHIP", order.getOrderNumber());
        } else {
            order.cancel();
            log.info("Order {} -> CANCELLED (we don't deliver to '{}')", order.getOrderNumber(), country);
        }
    }

    private static String normalise(String country) {
        return country == null ? "" : country.trim().toLowerCase(Locale.ROOT);
    }
}
