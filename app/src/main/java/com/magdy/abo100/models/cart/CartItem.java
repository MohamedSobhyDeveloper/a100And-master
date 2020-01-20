package com.magdy.abo100.models.cart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CartItem implements Serializable {

    @SerializedName("unit")
    private Unit unit;

    @SerializedName("product")
    private Product product;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("color")
    private Color color;

    @SerializedName("price")
    private String price;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("colorcode")
    private String colorcode;

    @SerializedName("id")
    private int id;

    @SerializedName("client_id")
    private String clientId;

    private boolean isNotChecked;

    public boolean isNotChecked() {
        return isNotChecked;
    }

    public void setNotChecked(boolean notChecked) {
        isNotChecked = notChecked;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public String getColorcode() {
        return colorcode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}