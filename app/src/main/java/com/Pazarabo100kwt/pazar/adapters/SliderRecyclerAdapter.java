package com.Pazarabo100kwt.pazar.adapters;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.models.slider_models.SliderItem;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderRecyclerAdapter extends RecyclerView.Adapter<SliderRecyclerAdapter.Holder> {

    private Activity activity;
    private List<List<SliderItem>> sliderList;
    private int time, heightDp;

    public SliderRecyclerAdapter(Activity activity, List<List<SliderItem>> sliderList, int heightDp) {
        this.activity = activity;
        this.sliderList = sliderList;
        this.heightDp = heightDp;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(activity).inflate(R.layout.item_slider, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightDp);
        holder.pager.setLayoutParams(params);
        List<SliderItem> imageList = sliderList.get(i);
        ImageSlidersAdapter adapter = new ImageSlidersAdapter(activity, imageList);
        holder.pager.setOffscreenPageLimit(1);
        holder.pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Timer timer = new Timer(); // At this line a new Thread will be created
        switch (i) {
            case 0:
                time = 2000;
                break;
            case 1:
                time = 3500;
                break;
            case 2:
                time = 4500;
        }
        new Handler().postDelayed(() -> {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(() -> {
                        int page = holder.pager.getCurrentItem();
                        page++;
                        if (page == Integer.MAX_VALUE)
                            page = 0;
                        holder.pager.setCurrentItem(page, true);
                    });
                }
            }, 0, 5000); // delay
        }, (long) (time));
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.pager)
        ViewPager pager;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
