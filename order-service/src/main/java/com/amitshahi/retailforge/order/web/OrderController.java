package com.amitshahi.retailforge.order.web;


import com.amitshahi.retailforge.order.service.OrderService;
import com.amitshahi.retailforge.order.service.SecurityService;
import com.amitshahi.retailforge.order.web.dto.CreateOrderRequest;
import com.amitshahi.retailforge.order.web.dto.CreateOrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(
            OrderService orderService,
            SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        String username = securityService.getCurrentUsername();

        return orderService.createOrder(username, request);
    }
}
