package com.example.order.config;

import com.example.order.domain.exceptions.OrderIdDoesntExistException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.model.StateOfOrder;
import com.example.order.domain.valueobjects.Address;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Price;
import com.example.order.domain.valueobjects.Product;
import com.example.order.service.IOrderService;
import com.example.order.service.forms.OrderForm;
import com.example.order.service.forms.OrderItemForm;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final IOrderService orderService;

    //    @NonNull Product product, @NonNull Price priceOfItem, @NonNull Integer totalItems, @NonNull Code discountCode) {
    @PostConstruct
    public void initData() {
        OrderForm orderForm = new OrderForm();
        Product product = new Product("Product", Price.valueOf(50));
        Product product2 = new Product("Product2", Price.valueOf(100));
        Product product3 = new Product("Product3", Price.valueOf(200));
        orderForm.setAddress(new Address("address", 10));
        orderForm.setCode(Code.Lsd34t);
        orderForm.setItemsInOrder(Arrays.asList(new OrderItem(product, 3, Code.Lsd34t),
                new OrderItem(product2, 6, Code.Lsd34t)));
        OrderId newOrderId = orderService.makeOrder(orderForm);
        this.orderService.addItemToOrder(newOrderId, product3, 9, null);
    }
}