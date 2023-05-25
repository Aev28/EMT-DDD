package com.example.order.domain.valueobjects;

import com.example.sharedkernel.domain.ValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Product implements ValueObject {
    private final String nameOfProduct;
    private final Price price;

    private Product() {
        this.nameOfProduct="";
        this.price = Price.valueOf(0);
    }

    @JsonCreator
    public Product(@JsonProperty("nameOfProduct") String nameOfProduct,
                   @JsonProperty("price") Price price) {
        this.nameOfProduct = nameOfProduct;
        this.price = price;
    }
}
