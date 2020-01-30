package com.Pazarabo100kwt.pazar.models.cart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data implements Serializable {

    @SerializedName("total")
    private double total;

    @SerializedName("cart")
    private List<CartItem> cart;

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public List<CartItem> getCart() {
        return cart;
    }
}