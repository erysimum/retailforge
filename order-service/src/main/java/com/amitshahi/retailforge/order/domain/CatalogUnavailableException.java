package com.amitshahi.retailforge.order.domain;

public class CatalogUnavailableException extends RuntimeException {

    public CatalogUnavailableException(String message) {
        super(message);
    }
}
