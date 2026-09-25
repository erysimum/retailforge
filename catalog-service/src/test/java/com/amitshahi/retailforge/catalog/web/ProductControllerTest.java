package com.amitshahi.retailforge.catalog.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.amitshahi.retailforge.catalog.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
class ProductControllerTest extends AbstractIntegrationTest {

    @Test
    void shouldReturnFirstPageWhenPageIsNotSpecified() {
        given().contentType("application/json")
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(5))
                .body("totalElements", equalTo(10))
                .body("pageNumber", equalTo(1))
                .body("totalPages", equalTo(2))
                .body("isFirst", equalTo(true))
                .body("isLast", equalTo(false))
                .body("hasNext", equalTo(true))
                .body("hasPrevious", equalTo(false));
    }

    @Test
    void shouldReturnFirstPageOfProducts() {
        given().contentType("application/json")
                .when()
                .get("/api/products?page=1")
                .then()
                .statusCode(200)
                .body("data", hasSize(5))
                .body("totalElements", equalTo(10))
                .body("pageNumber", equalTo(1))
                .body("totalPages", equalTo(2))
                .body("isFirst", equalTo(true))
                .body("isLast", equalTo(false))
                .body("hasNext", equalTo(true))
                .body("hasPrevious", equalTo(false));
    }

    @Test
    void shouldReturnLastPageOfProducts() {
        given().contentType("application/json")
                .when()
                .get("/api/products?page=2")
                .then()
                .statusCode(200)
                .body("data", hasSize(5))
                .body("totalElements", equalTo(10))
                .body("pageNumber", equalTo(2))
                .body("totalPages", equalTo(2))
                .body("isFirst", equalTo(false))
                .body("isLast", equalTo(true))
                .body("hasNext", equalTo(false))
                .body("hasPrevious", equalTo(true));
    }

    @Test
    void shouldReturnLastPageWhenRequestedPageIsOutOfRange() {
        given().contentType("application/json")
                .when()
                .get("/api/products?page=3")
                .then()
                .statusCode(200)
                .body("data", hasSize(0))
                .body("totalElements", equalTo(10))
                .body("pageNumber", equalTo(2))
                .body("totalPages", equalTo(2))
                .body("isFirst", equalTo(false))
                .body("isLast", equalTo(true))
                .body("hasNext", equalTo(false))
                .body("hasPrevious", equalTo(true));
    }

    @Test
    void shouldReturnProductBySku() {
        given().contentType("application/json")
                .when()
                .get("/api/products/RF-BED-001")
                .then()
                .statusCode(200)
                .body("sku", equalTo("RF-BED-001"))
                .body("name", equalTo("Classic Cotton Sheet Set"))
                .body("description", equalTo("300 thread count cotton sheet set in crisp white"))
                .body("price", equalTo(89.0f));
    }

    @Test
    void shouldReturn404WhenSkuNotFound() {
        given().contentType("application/problem+json")
                .when()
                .get("/api/products/NOPE-999")
                .then()
                .statusCode(404)
                .body("title", equalTo("Product Not Found"))
                .body("detail", equalTo("Product with sku NOPE-999 is not found!"))
                .body("status", equalTo(404));
    }
}
