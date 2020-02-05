package com.Pazarabo100kwt.pazar.models.slider_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderItem implements Serializable {
    @SerializedName("subcategory_id")
    private String subcategoryId;
    @SerializedName("category_id")
    private String categoryId;
    private String photo;
    private int id;

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
