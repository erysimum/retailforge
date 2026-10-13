package com.amitshahi.retailforge.order.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amitshahi.retailforge.order.service.OrderService;
import com.amitshahi.retailforge.order.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerValidationTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    @MockitoBean
    SecurityService securityService;

    @BeforeEach
    void setUp() {
        when(securityService.getCurrentUsername()).thenReturn("amit");
    }

    @Test
    void shouldReturnBadRequestWhenItemsAreEmpty() throws Exception {
        String payload = """
                {
                    "items": [],
                    "customer": {"name": "Amit", "email": "amit@example.com", "phone": "0400000000"},
                    "deliveryAddress": {"line1": "1 St", "city": "Melbourne", "state": "VIC", "zipCode": "3000", "country": "Australia"}
                }
                """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        String payload = """
                {
                    "items": [{"code": "RF-BED-001", "name": "Sheet Set", "price": 89.00, "quantity": 1}],
                    "customer": {"name": "Amit", "email": "not-an-email", "phone": "0400000000"},
                    "deliveryAddress": {"line1": "1 St", "city": "Melbourne", "state": "VIC", "zipCode": "3000", "country": "Australia"}
                }
                """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }
}
