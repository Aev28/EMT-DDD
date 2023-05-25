package com.example.order.domain.valueobjects;

import com.example.sharedkernel.domain.ValueObject;
import lombok.Getter;

@Getter
public class Price implements ValueObject {
    private final double price;

    private Price() {
        price = 0.0;
    }

    public Price(double price) {
        this.price = price;
    }

    public static Price valueOf(double amount) {
        return new Price(amount);
    }
}
