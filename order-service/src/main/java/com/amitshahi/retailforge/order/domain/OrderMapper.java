package com.amitshahi.retailforge.order.domain;

import com.amitshahi.retailforge.order.web.dto.CreateOrderRequest;

public class OrderMapper {
   public  static OrderEntity toEntity(CreateOrderRequest request, String username, String orderNumber) {
        OrderEntity order = new OrderEntity(
                orderNumber,
                username,
                request.customer(),
                request.deliveryAddress()
        );

        request.items().forEach(item ->
                order.addItem(new OrderItemEntity(
                        item.code(),
                        item.name(),
                        item.price(),
                        item.quantity()
                ))
        );

        return order;
    }
}
