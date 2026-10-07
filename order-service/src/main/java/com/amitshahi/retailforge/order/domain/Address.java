package com.amitshahi.retailforge.order.domain;

import jakarta.validation.constraints.NotBlank;

public record Address(
        @NotBlank(message = "Address line 1 is required")
        String line1,

        String line2,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "ZIP code is required")
        String zipCode,

        @NotBlank(message = "Country is required")
        String country
) {}