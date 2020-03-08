package com.Pazarabo100kwt.pazar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.activities.ProductDetailsActivity;
import com.Pazarabo100kwt.pazar.helpers.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.helpers.RetrofitModel;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.search_products.Product;
import com.Pazarabo100kwt.pazar.models.wishlist_models.ErrorWishListResponse;
import com.Pazarabo100kwt.pazar.models.wishlist_models.WishlistResponse;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.Holder> {


    private Context context;
    private List<Product> list;
    private RelativeLayout progress;

    public ProductsAdapter(Context context, List<Product> list, RelativeLayout progress) {
        this.context = context;
        this.list = list;
        this.progress = progress;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product product = list.get(position);
        if (product.getLogo() != null)
            Glide.with(context).load(product.getLogo()).into(holder.image);
        holder.favorite.setChecked(product.isWishlist());
        holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                changeFavorite(product.getId(), buttonView);
            }
        });
        holder.name.setText(product.getName());
        if (product.getProDetails().get(0).getNewprice() != null && !product.getProDetails().get(0).getNewprice().equals("")) {
            holder.priceOld.setVisibility(View.VISIBLE);
            holder.discount.setVisibility(View.VISIBLE);
            holder.price.setText(product.getProDetails().get(0).getNewprice() + " " + context.getString(R.string.kd));
            holder.priceOld.setText(product.getProDetails().get(0).getPrice() + " " + context.getString(R.string.kd));
            holder.priceOld.setPaintFlags(holder.priceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        } else {
            holder.priceOld.setVisibility(View.GONE);
            holder.discount.setVisibility(View.GONE);
            holder.price.setText(product.getProDetails().get(0).getPrice() + " " + context.getString(R.string.kd));

        }
        holder.productId.setText(product.getProductNo());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra(StaticMembers.PRODUCT, product);
            context.startActivity(intent);
        });
    }

    private void changeFavorite(long id, CompoundButton buttonView) {
        progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            StaticMembers.openLogin(context);
        } else {
            Call<WishlistResponse> call = RetrofitModel.getApi(context).toggleWishlist(id);
            call.enqueue(new CallbackRetrofit<WishlistResponse>(context) {
                @Override
                public void onResponse(@NotNull Call<WishlistResponse> call, @NotNull Response<WishlistResponse> response) {
                    progress.setVisibility(View.GONE);
                    WishlistResponse result = response.body();
                    if (response.isSuccessful() && result != null) {
                        StaticMembers.toastMessageSuccess(context, result.getMessage());
                    } else {
                        buttonView.setChecked(!buttonView.isChecked());
                        try {
                            ErrorWishListResponse errorLoginResponse = null;
                            ResponseBody errBody = response.errorBody();
                            String s = errBody.string();
                            StaticMembers.checkLoginRequired(errBody, context);
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(s, ErrorWishListResponse.class);
                                if (errorLoginResponse != null) {
                                    StaticMembers.toastMessageFailed(context, errorLoginResponse.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            StaticMembers.toastMessageFailed(context, context.getString(R.string.connection_error));
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<WishlistResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

    private Context getBaseContext() {
        return context;
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

        @BindView(R.id.subCode)
        TextView subCode;

        @BindView(R.id.priceOld)
        TextView priceOld;
        @BindView(R.id.discount)
        TextView discount;
        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
