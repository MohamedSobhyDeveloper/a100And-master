package com.magdy.abo100.models.order_models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Result{

	@SerializedName("payment_way")
	private Object paymentWay;

	@SerializedName("shippedate")
	private Object shippedate;

	@SerializedName("item")
	private List<ItemItem> item;

	@SerializedName("shipedto")
	private Object shipedto;

	@SerializedName("order_status_id")
	private Object orderStatusId;

	@SerializedName("promocode")
	private String promocode;

	@SerializedName("inv_number")
	private Object invNumber;

	@SerializedName("client_id")
	private String clientId;

	@SerializedName("order_date")
	private String orderDate;

	@SerializedName("order_status_payment")
	private Object orderStatusPayment;

	@SerializedName("total_amount")
	private String totalAmount;

	@SerializedName("last_updt")
	private String lastUpdt;

	@SerializedName("id")
	private int id;

	public void setPaymentWay(Object paymentWay){
		this.paymentWay = paymentWay;
	}

	public Object getPaymentWay(){
		return paymentWay;
	}

	public void setShippedate(Object shippedate){
		this.shippedate = shippedate;
	}

	public Object getShippedate(){
		return shippedate;
	}

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}

	public void setShipedto(Object shipedto){
		this.shipedto = shipedto;
	}

	public Object getShipedto(){
		return shipedto;
	}

	public void setOrderStatusId(Object orderStatusId){
		this.orderStatusId = orderStatusId;
	}

	public Object getOrderStatusId(){
		return orderStatusId;
	}

	public void setPromocode(String promocode){
		this.promocode = promocode;
	}

	public String getPromocode(){
		return promocode;
	}

	public void setInvNumber(Object invNumber){
		this.invNumber = invNumber;
	}

	public Object getInvNumber(){
		return invNumber;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}

	public void setOrderDate(String orderDate){
		this.orderDate = orderDate;
	}

	public String getOrderDate(){
		return orderDate;
	}

	public void setOrderStatusPayment(Object orderStatusPayment){
		this.orderStatusPayment = orderStatusPayment;
	}

	public Object getOrderStatusPayment(){
		return orderStatusPayment;
	}

	public void setTotalAmount(String totalAmount){
		this.totalAmount = totalAmount;
	}

	public String getTotalAmount(){
		return totalAmount;
	}

	public void setLastUpdt(String lastUpdt){
		this.lastUpdt = lastUpdt;
	}

	public String getLastUpdt(){
		return lastUpdt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}