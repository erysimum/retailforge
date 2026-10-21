package com.amitshahi.retailforge.order.clients;

import com.amitshahi.retailforge.order.domain.CatalogUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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

    @CircuitBreaker(name = "catalog", fallbackMethod = "getProductBySkuFallback")
    @Retry(name = "catalog", fallbackMethod = "getProductBySkuFallback")
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

    Optional<ProductResponse> getProductBySkuFallback(String sku, Throwable throwable) {
        System.out.println(throwable.getMessage());
        throw new CatalogUnavailableException(
                "Unable to validate product " + sku + " — catalog service is unavailable");
    }
}
