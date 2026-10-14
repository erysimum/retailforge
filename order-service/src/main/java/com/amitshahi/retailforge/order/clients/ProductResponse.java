package com.amitshahi.retailforge.order.clients;

import java.math.BigDecimal;

public record ProductResponse(String sku, String name, String description, BigDecimal price) {}
