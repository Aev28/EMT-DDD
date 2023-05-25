package com.example.order.domain.model;

import com.example.order.domain.valueobjects.Address;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Product;
import com.example.sharedkernel.domain.AbstractEntity;
import lombok.Getter;
import lombok.NonNull;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    private LocalDateTime dateOfPurchase;

    private Address address;

    @Enumerated(EnumType.STRING)
    private StateOfOrder orderStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> itemsInOrder;

    public Order() {
        super(OrderId.randomId(OrderId.class));
        this.itemsInOrder = new HashSet<>();
    }

    public Order(LocalDateTime dateOfPurchase, Address address, StateOfOrder orderStatus) {
        super(OrderId.randomId(OrderId.class));
        this.dateOfPurchase = dateOfPurchase;
        this.address = address;
        this.orderStatus = orderStatus;
        this.itemsInOrder = new HashSet<>();
    }

    public void setStateCancelled(){
        orderStatus = StateOfOrder.CANCELLED;
    }

    public Double totalPriceOfOrder() {
        return itemsInOrder.stream().mapToDouble(OrderItem::price).sum();
    }

    public OrderItem addItemToOrder(@NonNull Product product, @NonNull Integer totalItems, Code code) {
        Objects.requireNonNull(product,"Product must not be null");
        var item = new OrderItem(product, totalItems, code);
        itemsInOrder.add(item);
        return item;
    }

    public void deleteItemFromOrder(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId,"Order item must not be null");
        itemsInOrder.removeIf(v->v.getId().equals(orderItemId));
    }
}
