package com.magdy.abo100.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.magdy.abo100.R;
import com.magdy.abo100.activities.ProductDetailsActivity;
import com.magdy.abo100.helpers.CallbackRetrofit;
import com.magdy.abo100.helpers.PrefManager;
import com.magdy.abo100.helpers.RetrofitModel;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.offers_models.DataItem;
import com.magdy.abo100.models.wishlist_models.ErrorWishListResponse;
import com.magdy.abo100.models.wishlist_models.WishlistResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

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
