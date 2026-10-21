package com.amitshahi.retailforge.order.web;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.amitshahi.retailforge.order.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

public class OrderControllerIntegrationTest extends AbstractIntegrationTest {
    @Test
    void shouldCreateOrderSuccessfully() {

        wireMock.stubFor(get(urlEqualTo("/api/products/RF-BED-001")).willReturn(okJson("""
                        {
                            "sku": "RF-BED-001",
                            "name": "Classic Cotton Sheet Set",
                            "description": "300 thread count cotton",
                            "price": 89.00
                        }
                        """)));
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
