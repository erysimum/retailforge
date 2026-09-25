package com.amitshahi.retailforge.catalog.products;

import java.math.BigDecimal;

public record Product(String sku, String name, String description, BigDecimal price) {}
