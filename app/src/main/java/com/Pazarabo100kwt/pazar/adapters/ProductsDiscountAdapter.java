package com.Pazarabo100kwt.pazar.adapters;

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
import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.activities.ProductDetailsActivity;
import com.Pazarabo100kwt.pazar.retrofit.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.retrofit.RetrofitModel;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.search_products.Product;
import com.Pazarabo100kwt.pazar.models.wishlist_models.ErrorWishListResponse;
import com.Pazarabo100kwt.pazar.models.wishlist_models.WishlistResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProductsDiscountAdapter extends RecyclerView.Adapter<ProductsDiscountAdapter.Holder> {
    private Context context;
    private List<Product> list;
    private RelativeLayout progress;

    public ProductsDiscountAdapter(Context context, List<Product> list, RelativeLayout progress) {
        this.context = context;
        this.list = list;
        this.progress = progress;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_product_discount, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product product = list.get(position);
        if (product.getLogo() != null)
            Glide.with(context).load(product.getLogo()).fitCenter().into(holder.image);
        holder.favorite.setChecked(product.isWishlist());
        holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                changeFavorite(product.getId(), buttonView);
            }
        });
        if (product.getProDetails() != null &&
                !product.getProDetails().isEmpty() &&
                !product.getProDetails().get(0).getDiscount().equals("0")) {
            if (!product.getProDetails().get(0).getSubcode().isEmpty() &&
                    !product.getProDetails().get(0).getSubcode().equals("0")) {
                holder.subCode.setVisibility(View.VISIBLE);
                holder.subCode.setText(product.getProDetails().get(0).getSubcode());
            } else
                holder.subCode.setVisibility(View.GONE);
        }
        else
            holder.subCode.setVisibility(View.GONE);
        holder.name.setText(product.getName());
        holder.price.setText(product.getProDetails().get(0).getPrice());
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
                        //StaticMembers.toastMessageShort(context, result.getMessage());
                    } else {
                        buttonView.setChecked(!buttonView.isChecked());

                        try {
                            ErrorWishListResponse errorLoginResponse = null;
                            ResponseBody errBody = response.errorBody();
                            String s = errBody.string();
                            StaticMembers.checkLoginRequired(response.errorBody(), context);
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(s, ErrorWishListResponse.class);
                                if (errorLoginResponse != null) {
                                    StaticMembers.toastMessageFailed(context, errorLoginResponse.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            StaticMembers.toastMessageFailed(getBaseContext(), context.getString(R.string.connection_error));
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

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
