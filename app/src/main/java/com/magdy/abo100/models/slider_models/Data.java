package com.magdy.abo100.models.slider_models;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
	private List<SliderItem> slider3;
	private List<SliderItem> slider1;
	private List<SliderItem> slider2;

	public void setSlider3(List<SliderItem> slider3){
		this.slider3 = slider3;
	}

	public List<SliderItem> getSlider3(){
		return slider3;
	}

	public void setSlider1(List<SliderItem> slider1){
		this.slider1 = slider1;
	}

	public List<SliderItem> getSlider1(){
		return slider1;
	}

	public void setSlider2(List<SliderItem> slider2){
		this.slider2 = slider2;
	}

	public List<SliderItem> getSlider2(){
		return slider2;
	}
}