package com.amitshahi.retailforge.order.domain;

import com.amitshahi.retailforge.order.clients.CatalogServiceClient;
import com.amitshahi.retailforge.order.clients.ProductResponse;
import com.amitshahi.retailforge.order.web.dto.CreateOrderItemRequest;
import com.amitshahi.retailforge.order.web.dto.CreateOrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    private final CatalogServiceClient catalogServiceClient;

    public OrderValidator(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    public void validate(CreateOrderRequest request) {
        for (CreateOrderItemRequest item : request.items()) {
            ProductResponse product = catalogServiceClient
                    .getProductBySku(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid product code: " + item.code()));

            if (product.price().compareTo(item.price()) != 0) {
                throw new InvalidOrderException("Product price mismatch for " + item.code()
                        + ": expected " + product.price()
                        + " but got " + item.price());
            }
        }
    }
}
