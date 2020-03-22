
package com.Pazarabo100kwt.pazar.models.search_products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ColorList implements Serializable {

    @SerializedName("color")
    @Expose
    private Color color;
    @SerializedName("measures")
    @Expose
    private List<MeasureList> measures = null;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<MeasureList> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureList> measures) {
        this.measures = measures;
    }

}
