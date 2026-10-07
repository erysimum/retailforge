package com.amitshahi.retailforge.order.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_generator")
    @SequenceGenerator(name = "order_item_id_generator", sequenceName = "order_item_id_seq", allocationSize = 50)
    private Long id;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    protected OrderItemEntity() {
        // Required by JPA
    }

    public OrderItemEntity(String code, String name, BigDecimal price, Integer quantity) {
        this.code = Objects.requireNonNull(code);
        this.name = Objects.requireNonNull(name);
        this.price = Objects.requireNonNull(price);
        this.quantity = Objects.requireNonNull(quantity);

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (price.signum() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    void assignTo(OrderEntity order) {
        this.order = Objects.requireNonNull(order);
    }

    public void changeQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.quantity = quantity;
    }
}
