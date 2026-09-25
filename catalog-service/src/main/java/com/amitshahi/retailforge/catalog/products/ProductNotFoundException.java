package com.amitshahi.retailforge.catalog.products;

public class ProductNotFoundException extends RuntimeException {
    private ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException withSku(String sku) {
        return new ProductNotFoundException("Product with sku " + sku + " is not found!");
    }
}
