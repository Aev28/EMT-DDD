package com.example.order.domain.valueobjects;

import com.example.sharedkernel.domain.ValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Address implements ValueObject {
    private final String nameOfAddress;
    private final int houseNumber;

    private Address() {
        this.nameOfAddress = "";
        this.houseNumber = 0;
    }

    @JsonCreator
    public Address(@JsonProperty("nameOfAddress") String nameOfAddress,
                   @JsonProperty("houseNumber") int houseNumber) {
        this.nameOfAddress = nameOfAddress;
        this.houseNumber = houseNumber;
    }
}
