package com.amitshahi.retailforge.order.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_seq", allocationSize = 50)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private String username;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "customer_name", nullable = false)),
        @AttributeOverride(name = "email", column = @Column(name = "customer_email", nullable = false)),
        @AttributeOverride(name = "phone", column = @Column(name = "customer_phone", nullable = false))
    })
    private Customer customer;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "line1", column = @Column(name = "delivery_address_line1", nullable = false)),
        @AttributeOverride(name = "line2", column = @Column(name = "delivery_address_line2")),
        @AttributeOverride(name = "city", column = @Column(name = "delivery_address_city", nullable = false)),
        @AttributeOverride(name = "state", column = @Column(name = "delivery_address_state", nullable = false)),
        @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_address_zip_code", nullable = false)),
        @AttributeOverride(name = "country", column = @Column(name = "delivery_address_country", nullable = false))
    })
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(length = 500)
    private String comments;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemEntity> items = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected OrderEntity() {
        // Required by JPA
    }

    public OrderEntity(String orderNumber, String username, Customer customer, Address deliveryAddress) {
        this.orderNumber = Objects.requireNonNull(orderNumber);
        this.username = Objects.requireNonNull(username);
        this.customer = Objects.requireNonNull(customer);
        this.deliveryAddress = Objects.requireNonNull(deliveryAddress);

        this.status = OrderStatus.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public Set<OrderItemEntity> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void addItem(OrderItemEntity item) {
        Objects.requireNonNull(item);

        items.add(item);
        item.assignTo(this);
    }

    public void setStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
