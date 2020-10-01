package com.Pazarabo100kwt.pazar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.bumptech.glide.Glide;
import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.activities.SearchProductsActivity;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.slider_models.SliderItem;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ImageSliderAdapter extends LoopingPagerAdapter<SliderItem> {

    public ImageSliderAdapter(Context context, ArrayList<SliderItem> itemList) {
        super(context, itemList, true);
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.item_image_slider, container, false);
    }

    @Override
    protected void bindView(View view, int listPosition, int viewType) {
        ImageView imageView = view.findViewById(R.id.image);
//        Glide.with(context).load(getItem(listPosition).getPhoto()).fitCenter().into(imageView);
        Glide.with(context)
                .asBitmap()
//                .apply(new RequestOptions().override(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .load(getItem(listPosition).getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);

        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, SearchProductsActivity.class);
            intent.putExtra(StaticMembers.SUB_CATEGORY_ID, getItem(listPosition).getSubcategoryId());
            intent.putExtra(StaticMembers.CATEGORY_ID, getItem(listPosition).getCategoryId());
            context.startActivity(intent);
        });
    }
}