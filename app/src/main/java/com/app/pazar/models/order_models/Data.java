package com.app.pazar.models.order_models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}
}