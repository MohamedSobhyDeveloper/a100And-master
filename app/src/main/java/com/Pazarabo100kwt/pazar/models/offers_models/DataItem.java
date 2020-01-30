package com.Pazarabo100kwt.pazar.models.offers_models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("subcategory_id")
	private String subcategoryId;

	@SerializedName("offer_no")
	private String offerNo;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("serial")
	private String serial;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("logo")
	private String logo;

	@SerializedName("id")
	private int id;

	@SerializedName("tag")
	private String tag;

	@SerializedName("photos")
	private List<String> photos;

	public void setSubcategoryId(String subcategoryId){
		this.subcategoryId = subcategoryId;
	}

	public String getSubcategoryId(){
		return subcategoryId;
	}

	public void setOfferNo(String offerNo){
		this.offerNo = offerNo;
	}

	public String getOfferNo(){
		return offerNo;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setSerial(String serial){
		this.serial = serial;
	}

	public String getSerial(){
		return serial;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	public void setPhotos(List<String> photos){
		this.photos = photos;
	}

	public List<String> getPhotos(){
		return photos;
	}
}