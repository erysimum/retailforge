package com.amitshahi.retailforge.order.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final Set<String> supportedCountries;
    private final OrderEventRepository orderEventRepository;
    private final ObjectMapper objectMapper;

    public OrderProcessingService(
            OrderRepository orderRepository,
            @Value("${order.processing.supported-countries}") String[] supportedCountries,
            OrderEventRepository orderEventRepository,
            ObjectMapper objectMapper) {

        this.orderRepository = orderRepository;
        this.supportedCountries = Arrays.stream(supportedCountries)
                .map(OrderProcessingService::normalise)
                .collect(Collectors.toUnmodifiableSet());
        this.orderEventRepository = orderEventRepository;
        this.objectMapper = objectMapper;

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
        String eventId = UUID.randomUUID().toString();

        if (supportedCountries.contains(normalise(country))) {
            order.markReadyToShip();

            saveEvent(
                    eventId,
                    OrderEventType.ORDER_READY_TO_SHIP,
                    order,
                    new OrderReadyToShipEvent(
                            eventId,
                            order.getOrderNumber(),
                            order.getCustomer().name(),
                            order.getCustomer().email(),
                            LocalDateTime.now()));

            log.info("Order {} -> READY_TO_SHIP", order.getOrderNumber());
        } else {
            order.cancel();

            saveEvent(
                    eventId,
                    OrderEventType.ORDER_CANCELLED,
                    order,
                    new OrderCancelledEvent(
                            eventId,
                            order.getOrderNumber(),
                            order.getCustomer().name(),
                            order.getCustomer().email(),
                            "DELIVERY_COUNTRY_NOT_SUPPORTED",
                            LocalDateTime.now()));
            log.info("Order {} -> CANCELLED (we don't deliver to '{}')", order.getOrderNumber(), country);
        }
    }

    private void saveEvent(String eventId, OrderEventType type, OrderEntity order, Object payload) {

        String json = objectMapper.writeValueAsString(payload);

        orderEventRepository.save(new OrderEventEntity(eventId, type, order.getOrderNumber(), json));
    }

    private static String normalise(String country) {
        return country == null ? "" : country.trim().toLowerCase(Locale.ROOT);
    }
}
