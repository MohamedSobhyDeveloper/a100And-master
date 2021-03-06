package com.magdy.abo100.helpers;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.magdy.abo100.R;
import com.magdy.abo100.adapters.ImageSliderAdapter;
import com.magdy.abo100.adapters.ImageSlidersAdapter;
import com.magdy.abo100.models.slider_models.SliderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SliderHelper {
    private LoopingViewPager pager1, pager2, pager3;
    private ImageSliderAdapter adapter1, adapter2, adapter3;

    public SliderHelper(Activity activity, View view, List<ArrayList<SliderItem>> sliderList) {
        if (sliderList != null && sliderList.size() == 3) {
            adapter1 = new ImageSliderAdapter(activity, sliderList.get(0));
            pager1 = view.findViewById(R.id.pager1);
            pager1.setOffscreenPageLimit(1);
            pager1.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            pager1.resumeAutoScroll();

            pager2 = view.findViewById(R.id.pager2);
            adapter2 = new ImageSliderAdapter(activity, sliderList.get(1));
            pager2.setOffscreenPageLimit(1);
            pager2.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
            pager2.pauseAutoScroll();
            Handler handler2 = new Handler();
            handler2.postDelayed(() -> pager2.resumeAutoScroll(), 1500);


            pager3 = view.findViewById(R.id.pager3);
            adapter3 = new ImageSliderAdapter(activity, sliderList.get(2));
            pager3.setOffscreenPageLimit(1);
            pager3.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            pager3.pauseAutoScroll();
            Handler handler3 = new Handler();
            handler3.postDelayed(() -> pager3.resumeAutoScroll(), 2500);
        }
    }

    public void notifyAdapters() {
        if (adapter1 != null)
            adapter1.notifyDataSetChanged();
        if (adapter2 != null)
            adapter2.notifyDataSetChanged();
        if (adapter3 != null)
            adapter3.notifyDataSetChanged();
    }

    public void pauseAll()
    {
        pager1.pauseAutoScroll();
        pager2.pauseAutoScroll();
        pager3.pauseAutoScroll();
    }

    public void resumeAll(){
        pager1.resumeAutoScroll();
        new Handler().postDelayed(() -> pager2.resumeAutoScroll(), 1500);
        new Handler().postDelayed(() -> pager3.resumeAutoScroll(), 2500);
    }

}
