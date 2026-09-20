package com.amitshahi.retailforge.catalog.products;

import java.math.BigDecimal;

public record Product (
    String sk,
    String name,
    String description,
    BigDecimal price
){}
