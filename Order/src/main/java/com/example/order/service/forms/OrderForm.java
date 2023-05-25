package com.example.order.service.forms;

import com.example.order.domain.model.OrderItem;
import com.example.order.domain.valueobjects.Address;
import com.example.order.domain.valueobjects.Code;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderForm {
    @Valid
    @NotEmpty
    List<OrderItem> itemsInOrder;
    @NotNull
    Address address;
    @NotNull
    Code code;
}
