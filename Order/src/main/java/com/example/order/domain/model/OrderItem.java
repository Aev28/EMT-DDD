package com.example.order.domain.model;

import com.example.order.domain.valueobjects.Code;
import com.example.order.domain.valueobjects.Discount;
import com.example.order.domain.valueobjects.Price;
import com.example.order.domain.valueobjects.Product;
import com.example.sharedkernel.domain.AbstractEntity;
import com.example.sharedkernel.domain.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem extends AbstractEntity<OrderItemId> {

    private Product product;

    private Price priceOfItemWithDiscount;

    private Integer totalItems;

    public OrderItem() {
        super(DomainObjectId.randomId(OrderItemId.class));
    }

    public OrderItem(@NonNull Product product, @NonNull Integer totalItems, Code discountCode) {
        this.product = product;
        this.priceOfItemWithDiscount = Price.valueOf(calculatePriceWithDiscount(discountCode, product.getPrice()));
        this.totalItems = totalItems;
    }

    public double calculatePriceWithDiscount(Code code, Price price){
        return price.getPrice() - price.getPrice() * Discount.valueOfDiscount(code);
    }

    public Double price() {
        return priceOfItemWithDiscount.getPrice() * totalItems;
    }
}
