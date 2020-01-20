package com.magdy.abo100.models.cart;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Unit implements Serializable {

	@SerializedName("up_level")
	private String upLevel;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setUpLevel(String upLevel){
		this.upLevel = upLevel;
	}

	public String getUpLevel(){
		return upLevel;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}