
package com.Pazarabo100kwt.pazar.models.search_products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MeasureList implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("subcode")
    @Expose
    private String subcode;
    @SerializedName("serial")
    @Expose
    private Object serial;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("basicunit")
    @Expose
    private String basicunit;
    @SerializedName("unitsize")
    @Expose
    private String unitsize;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("newprice")
    @Expose
    private Object newprice;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("measure")
    @Expose
    private Measure measure;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public Object getSerial() {
        return serial;
    }

    public void setSerial(Object serial) {
        this.serial = serial;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBasicunit() {
        return basicunit;
    }

    public void setBasicunit(String basicunit) {
        this.basicunit = basicunit;
    }

    public String getUnitsize() {
        return unitsize;
    }

    public void setUnitsize(String unitsize) {
        this.unitsize = unitsize;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getNewprice() {
        return newprice;
    }

    public void setNewprice(Object newprice) {
        this.newprice = newprice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

}
