package com.Pazarabo100kwt.pazar.models.wishlist_models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("wishlist")
	private boolean wishlist;

	public void setWishlist(boolean wishlist){
		this.wishlist = wishlist;
	}

	public boolean isWishlist(){
		return wishlist;
	}
}