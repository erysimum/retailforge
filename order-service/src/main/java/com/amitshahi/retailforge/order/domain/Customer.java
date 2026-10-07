package com.amitshahi.retailforge.order.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Customer(
        @NotBlank(message = "Customer name is required")
        String name,

        @NotBlank(message = "Customer email is required")
        @Email(message = "Customer email must be valid")
        String email,

        @NotBlank(message = "Customer phone is required")
        String phone
)
{}