package com.amitshahi.retailforge.order.web.dto;

import com.amitshahi.retailforge.order.domain.Address;
import com.amitshahi.retailforge.order.domain.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateOrderRequest(
        @NotEmpty(message = "At least one order item is required")
        @Valid
        Set<CreateOrderItemRequest> items,

        @NotNull
        @Valid
        Customer customer,

        @NotNull
        @Valid
        Address deliveryAddress
) {
}
