package com.example.order.service;

import com.example.order.domain.exceptions.OrderIdDoesntExistException;
import com.example.order.domain.exceptions.OrderItemIdDoesntExistException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.model.OrderItemId;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Product;
import com.example.order.service.forms.OrderForm;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findAll();

    Optional<Order> findById(OrderId id);

    OrderItem addItemToOrder(OrderId orderId, Product product, Integer totalItems, Code code) throws OrderIdDoesntExistException;

    void deleteItemFromOrder(OrderId orderId, OrderItemId orderItemId) throws OrderIdDoesntExistException, OrderItemIdDoesntExistException;

    OrderId makeOrder(OrderForm orderForm);

    void cancelOrder(OrderId id);
}

