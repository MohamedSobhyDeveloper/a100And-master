package com.Pazarabo100kwt.pazar.models.order_models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ItemItem{

	@SerializedName("unit")
	private String unit;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("price")
	private String price;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("product_no")
	private String product_no;

	@SerializedName("colorcode")
	private String colorcode;

	@SerializedName("id")
	private int id;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("subcode")
	private String subcode;

	@SerializedName("product_name")
	private String product_name;

	@SerializedName("color_name")
	private String color_name;

	@SerializedName("hastag")
	private String hastag;

	public String getHastag() {
		return hastag;
	}

	public void setHastag(String hastag) {
		this.hastag = hastag;
	}

	public String getProduct_no() {
		return product_no;
	}

	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}

	public String getColor_name() {
		return color_name;
	}

	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getSubcode() {
		return subcode;
	}

	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}

	public void setUnit(String unit){
		this.unit = unit;
	}

	public String getUnit(){
		return unit;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setColorcode(String colorcode){
		this.colorcode = colorcode;
	}

	public String getColorcode(){
		return colorcode;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}