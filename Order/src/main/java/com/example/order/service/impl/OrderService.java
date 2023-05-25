package com.example.order.service.impl;

import com.example.order.domain.exceptions.OrderIdDoesntExistException;
import com.example.order.domain.exceptions.OrderItemIdDoesntExistException;
import com.example.order.domain.model.*;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Product;
import com.example.order.service.IOrderService;
import com.example.order.service.forms.OrderForm;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final Validator validator;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderItem addItemToOrder(OrderId orderId, Product product, Integer totalItems, Code code) throws OrderIdDoesntExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdDoesntExistException::new);
        OrderItem orderItem = order.addItemToOrder(product, totalItems, code);
        orderRepository.saveAndFlush(order);
        return orderItem;
    }

    @Override
    public void deleteItemFromOrder(OrderId orderId, OrderItemId orderItemId) throws OrderIdDoesntExistException, OrderItemIdDoesntExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdDoesntExistException::new);
        order.deleteItemFromOrder(orderItemId);
        orderRepository.saveAndFlush(order);
    }

    @Override
    public OrderId makeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm);
        var constraintViolations = validator.validate(orderForm);
        if (constraintViolations.size()>0) {
            throw new RuntimeException("The order form is not valid");
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        return newOrder.getId();
    }

    @Override
    public void cancelOrder(OrderId id) {
        Order order = this.findById(id).orElseThrow(OrderIdDoesntExistException::new);
        order.setStateCancelled();
        orderRepository.saveAndFlush(order);
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(LocalDateTime.now(), orderForm.getAddress(), StateOfOrder.CREATED);
        orderForm.getItemsInOrder().forEach(item->order.addItemToOrder(item.getProduct(), item.getTotalItems(), orderForm.getCode()));
        return order;
    }
}
