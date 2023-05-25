package com.example.order;

import com.example.order.domain.exceptions.OrderIdDoesntExistException;
import com.example.order.domain.exceptions.OrderItemIdDoesntExistException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.model.StateOfOrder;
import com.example.order.domain.valueobjects.Address;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Price;
import com.example.order.domain.valueobjects.Product;
import com.example.order.service.forms.OrderForm;
import com.example.order.service.impl.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OrderApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void testMakeOrder() {
        OrderForm orderForm = new OrderForm();
        Product product = new Product("Product", Price.valueOf(50));
        Product product2 = new Product("Product2", Price.valueOf(100));
        orderForm.setAddress(new Address("address", 10));
        orderForm.setCode(Code.Lsd34t);
        orderForm.setItemsInOrder(Arrays.asList(new OrderItem(product, 3, Code.Lsd34t),
                new OrderItem(product2, 6, Code.Lsd34t)));
        OrderId newOrderId = orderService.makeOrder(orderForm);
        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdDoesntExistException::new);
        Assertions.assertNotNull(newOrder.getId());
    }

    @Test
    void testDeleteItemFromOrder(){
        OrderForm orderForm = new OrderForm();
        Product product = new Product("Product", Price.valueOf(50));
        Product product2 = new Product("Product2", Price.valueOf(100));
        Product product3 = new Product("Product3", Price.valueOf(200));
        orderForm.setAddress(new Address("address", 10));
        orderForm.setCode(Code.Lsd34t);
        orderForm.setItemsInOrder(Arrays.asList(new OrderItem(product, 3, Code.Lsd34t),
                new OrderItem(product2, 6, Code.Lsd34t)));
        OrderId newOrderId = orderService.makeOrder(orderForm);
        OrderItem orderItem = this.orderService.addItemToOrder(newOrderId, product3, 9, Code.Lsd34t);
        Assertions.assertThrows(NullPointerException.class, () ->{
                orderService.deleteItemFromOrder(newOrderId, orderItem.getId());
        });
    }

    @Test
    void testDiscount() {
        OrderForm orderForm = new OrderForm();
        Product product = new Product("Product", Price.valueOf(50));
        Product product2 = new Product("Product2", Price.valueOf(100));
        Product product3 = new Product("Product3", Price.valueOf(200));
        orderForm.setAddress(new Address("address", 10));
        orderForm.setCode(Code.Lsd34t);
        orderForm.setItemsInOrder(Arrays.asList(new OrderItem(product, 3, Code.Lsd34t),
                new OrderItem(product2, 6, Code.Lsd34t)));
        OrderId newOrderId = orderService.makeOrder(orderForm);
        this.orderService.addItemToOrder(newOrderId, product3, 9, Code.Lsd34t);
        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdDoesntExistException::new);
        Assertions.assertEquals(2040, newOrder.totalPriceOfOrder()); //6x80(20% discount) + 3x40(20% discount) + 9x160(20% discount)
    }

    @Test
    void testStateCreatedAndCancelled() {
        OrderForm orderForm = new OrderForm();
        Product product = new Product("Product", Price.valueOf(50));
        orderForm.setAddress(new Address("address", 10));
        orderForm.setCode(Code.Lsd34t);
        orderForm.setItemsInOrder(List.of(new OrderItem(product, 3, Code.Lsd34t)));
        OrderId newOrderId = orderService.makeOrder(orderForm);
        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdDoesntExistException::new);
        Assertions.assertEquals(newOrder.getOrderStatus(), StateOfOrder.CREATED);
        newOrder.setStateCancelled();
        Assertions.assertEquals(newOrder.getOrderStatus(), StateOfOrder.CANCELLED);
    }
}
