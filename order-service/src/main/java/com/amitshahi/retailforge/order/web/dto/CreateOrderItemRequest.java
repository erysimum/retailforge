package com.amitshahi.retailforge.order.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateOrderItemRequest(
        @NotBlank(message = "Product code is required") String code,

        @NotBlank(message = "Product name is required") String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,

        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity) {}
