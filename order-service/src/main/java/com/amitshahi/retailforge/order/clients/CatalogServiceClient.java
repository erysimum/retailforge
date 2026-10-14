package com.amitshahi.retailforge.order.clients;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class CatalogServiceClient {
    private final RestClient catalogRestClient;

    CatalogServiceClient(RestClient catalogRestClient) {
        this.catalogRestClient = catalogRestClient;
    }

    public Optional<ProductResponse> getProductBySku(String sku) {
        try {
            ProductResponse product = catalogRestClient
                    .get()
                    .uri("/api/products/{sku}", sku)
                    .retrieve()
                    .body(ProductResponse.class);

            return Optional.ofNullable(product);

        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        }
    }
}
