package com.app.pazar.models.search_products;

import java.io.Serializable;

public class MeasureItem implements Serializable {
	private String upLevel;
	private String name;
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

	@Override
 	public String toString(){
		return 
			"MeasureItem{" + 
			"up_level = '" + upLevel + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
