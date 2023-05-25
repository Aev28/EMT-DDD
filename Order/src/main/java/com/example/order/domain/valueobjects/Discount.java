package com.example.order.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Discount {
    private final Code code;

    private Discount() {
        this.code = null;
    }

    @JsonCreator
    public Discount(@JsonProperty("code") Code code){
        this.code = code;
    }

    public static double valueOfDiscount(Code code) {
        double discount = 0.0;
        if(code == null){
            return 0.0;
        }
        if (code == Code.Lsd34t) {
            discount = 0.2;
        } else {
            discount = 0.0;
        }
        return discount;
    }
}
