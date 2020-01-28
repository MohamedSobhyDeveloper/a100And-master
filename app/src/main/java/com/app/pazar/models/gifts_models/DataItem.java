package com.app.pazar.models.gifts_models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("datains")
	private String datains;

	@SerializedName("amount")
	private String amount;

	@SerializedName("usedbefore")
	private String usedbefore;

	@SerializedName("code")
	private String code;

	@SerializedName("validtill")
	private String validtill;

	@SerializedName("invoicetotalprice")
	private String invoicetotalprice;

	@SerializedName("id")
	private int id;

	@SerializedName("startvalid")
	private String startvalid;

	public void setDatains(String datains){
		this.datains = datains;
	}

	public String getDatains(){
		return datains;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setUsedbefore(String usedbefore){
		this.usedbefore = usedbefore;
	}

	public String getUsedbefore(){
		return usedbefore;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setValidtill(String validtill){
		this.validtill = validtill;
	}

	public String getValidtill(){
		return validtill;
	}

	public void setInvoicetotalprice(String invoicetotalprice){
		this.invoicetotalprice = invoicetotalprice;
	}

	public String getInvoicetotalprice(){
		return invoicetotalprice;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStartvalid(String startvalid){
		this.startvalid = startvalid;
	}

	public String getStartvalid(){
		return startvalid;
	}
}