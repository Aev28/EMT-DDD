package com.example.order.xport.rest;

import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItemId;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Product;
import com.example.order.service.IOrderService;
import com.example.order.service.forms.OrderForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @GetMapping
    public List<Order> listAll() {
        return this.orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable OrderId id){
        return this.orderService.findById(id)
                .map(order -> ResponseEntity.ok().body(order))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public void add(@PathVariable OrderId id,
                    @RequestBody Product product,
                    @RequestBody Integer totalItems,
                    @RequestBody Code code,
                    HttpServletResponse response) throws IOException {
        this.orderService.addItemToOrder(id, product, totalItems, code);
        response.sendRedirect("/api/order");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable OrderId id, @RequestBody OrderItemId orderItemId){
        this.orderService.deleteItemFromOrder(id, orderItemId);
        if(this.orderService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/makeOrder")
    public void makeOrder(@RequestBody OrderForm order, HttpServletResponse response) throws IOException {
        orderService.makeOrder(order);
        response.sendRedirect("/api/order");
    }

    @PostMapping("/cancelOrder/{id}")
    public void cancelOrder(@PathVariable OrderId id, HttpServletResponse response) throws IOException {
        orderService.cancelOrder(id);
        response.sendRedirect("/api/order");
    }
}