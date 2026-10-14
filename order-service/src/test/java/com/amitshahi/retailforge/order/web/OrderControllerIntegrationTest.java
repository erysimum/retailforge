package com.amitshahi.retailforge.order.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.amitshahi.retailforge.order.AbstractIntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class OrderControllerIntegrationTest extends AbstractIntegrationTest {
    @Test
    @Disabled("Order creation now calls catalog-service; needs WireMock stub — added in 3f")
    void shouldCreateOrderSuccessfully() {
        String payload = """
                {
                    "items": [
                        {"code": "RF-BED-001", "name": "Classic Cotton Sheet Set", "price": 89.00, "quantity": 2}
                    ],
                    "customer": {"name": "Amit Shahi", "email": "amit@example.com", "phone": "0400000000"},
                    "deliveryAddress": {
                        "line1": "1 Test St", "line2": null, "city": "Melbourne",
                        "state": "VIC", "zipCode": "3000", "country": "Australia"
                    }
                }
                """;

        given().contentType("application/json")
                .body(payload)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(201)
                .body("orderNumber", notNullValue());
    }
}
