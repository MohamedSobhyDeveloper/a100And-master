package com.Pazarabo100kwt.pazar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.models.offers_models.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.Holder> {
    private Context context;
    private List<DataItem> list;
    private RelativeLayout progress;

    public OffersAdapter(Context context, List<DataItem> list, RelativeLayout progress) {
        this.context = context;
        this.list = list;
        this.progress = progress;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem product = list.get(position);
        if (product.getLogo() != null)
            Glide.with(context).load(product.getLogo()).fitCenter().into(holder.image);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.productId.setText(product.getSerial());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.productId)
        TextView productId;

        @BindView(R.id.favorite)
        CheckBox favorite;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
