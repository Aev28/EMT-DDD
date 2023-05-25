package com.example.order.service.forms;

import com.example.order.domain.model.OrderId;
import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Product;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class OrderItemForm {
    OrderId orderId;

    @NotNull
    Product product;

    @Min(1)
    Integer totalItems;

    public OrderItemForm(Product product, Integer totalItems) {
        this.product = product;
        this.totalItems = totalItems;
    }
}
