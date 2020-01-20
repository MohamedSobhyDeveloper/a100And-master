package com.magdy.abo100.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magdy.abo100.R;
import com.magdy.abo100.activities.CartActivity;
import com.magdy.abo100.activities.LogInActivity;
import com.magdy.abo100.helpers.CallbackRetrofit;
import com.magdy.abo100.helpers.PrefManager;
import com.magdy.abo100.helpers.RetrofitModel;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.cart.AddCartResponse;
import com.magdy.abo100.models.cart.CartItem;
import com.magdy.abo100.models.cart.Data;
import com.magdy.abo100.models.cart.Product;
import com.magdy.abo100.models.cart.delete_cart_models.DeleteCartResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {
    private CartActivity activity;
    private RelativeLayout progress;
    private Data cartData;

    public CartAdapter(CartActivity activity, Data cartData, RelativeLayout progress) {
        this.activity = activity;
        this.cartData = cartData;
        this.progress = progress;
    }

    void changeTotal(double total) {
        cartData.setTotal(total);
        notifyItemChanged(cartData.getCart().size() );
    }

    public void setCartData(Data cartData) {
        this.cartData = cartData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(activity).inflate(R.layout.item_product_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.onBind();
    }

    private void changeCartItem(CartItem cartItem, int amount, TextView amountText) {
        // progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(activity).getAPIToken().isEmpty()) {
            Intent intent = new Intent(activity, LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            activity.startActivity(intent);
        } else {
            Call<AddCartResponse> call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                    amount, 0, 0);
            call.enqueue(new CallbackRetrofit<AddCartResponse>(activity) {
                @Override
                public void onResponse(@NotNull Call<AddCartResponse> call, @NotNull Response<AddCartResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                        StaticMembers.checkLoginRequired(response.errorBody(), activity);
                    } else activity.getCart();
                }

                @Override
                public void onFailure(@NotNull Call<AddCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    //  progress.setVisibility(View.GONE);
                }
            });
        }
    }

    public void deleteCartItem(int position) {
        CartItem cartItem = cartData.getCart().get(position);
        removeItem(position);
        // progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(activity).getAPIToken().isEmpty()) {
            Intent intent = new Intent(activity, LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            activity.startActivity(intent);
        } else {
            Call<DeleteCartResponse> call = RetrofitModel.getApi(activity).deleteCartItem(cartItem.getProductId());
            call.enqueue(new CallbackRetrofit<DeleteCartResponse>(activity) {
                @Override
                public void onResponse(@NotNull Call<DeleteCartResponse> call, @NotNull Response<DeleteCartResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        restoreItem(cartItem, position);
                        if (!response.message().isEmpty())
                            StaticMembers.toastMessageShort(activity, response.message());
                        StaticMembers.checkLoginRequired(response.errorBody(), activity);
                    } else activity.getCart();
                }

                @Override
                public void onFailure(@NotNull Call<DeleteCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    // progress.setVisibility(View.GONE);
                }
            });
        }
    }

    private void removeItem(int position) {
        cartData.getCart().remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartData.getCart().size());
    }

    private void restoreItem(CartItem item, int position) {
        cartData.getCart().add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return cartData.getCart().size() + 1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.remove)
        ImageButton remove;
        @BindView(R.id.add)
        ImageButton add;
        @BindView(R.id.amount)
        TextView amountText;
        @BindView(R.id.productId)
        TextView productId;
        @BindView(R.id.total)
        TextView total;
        @BindView(R.id.selected)
        CheckBox selected;
        @BindView(R.id.front)
        public LinearLayout front;
        @BindView(R.id.itemLayout)
        FrameLayout itemLayout;
        @BindView(R.id.totalLayout)
        LinearLayout totalLayout;
        @BindView(R.id.linear)
        LinearLayout linear;


        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind() {
            int position = getAdapterPosition();
            if (position < cartData.getCart().size()) {

                totalLayout.setVisibility(View.GONE);
                itemLayout.setVisibility(View.VISIBLE);
                CartItem cartItem = cartData.getCart().get(position);
                Product product = cartItem.getProduct();
                if (product != null) {
                    if (product.getPhotos() != null)
                        Glide.with(activity).load(product.getPhotos().get(0)).into(image);

                    name.setText(product.getName());
                    productId.setText(String.format(Locale.getDefault(), activity.getString(R.string.product_id_s), product.getProductNo()));
                    price.setText(String.format(Locale.getDefault(), activity.getString(R.string.s_kwd), cartItem.getPrice()));
                    itemView.setOnClickListener(v -> {
                /*Intent intent = new Intent(activity, ProductDetailsActivity.class);
                intent.putExtra(StaticMembers.PRODUCT, product);
                activity.startActivity(intent);*/
                    });
                    int amount = Integer.parseInt(cartItem.getQuantity());
                    amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                    if (amount < 2)
                        remove.setEnabled(false);
                    else remove.setEnabled(true);
                    add.setOnClickListener(v -> {
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount + 1));
                        changeCartItem(cartItem, amount + 1, amountText);
                        if (amount + 1 > 1)
                            remove.setEnabled(true);
               /* if (product.getProDetails().get(0).getCount() != null)
                    if (amount >= Integer.parseInt(product.getProDetails().get(0).getCount()))
                        add.setEnabled(false);*/
                    });
                    remove.setOnClickListener(v -> {
                        if (amount < 2)
                            remove.setEnabled(false);
                        else {
                            amountText.setText(String.format(Locale.getDefault(), "%d", amount - 1));
                            changeCartItem(cartItem, amount - 1, amountText);
                            if (amount - 1 < 2)
                                remove.setEnabled(false);
                   /* if (product.getProDetails().get(0).getCount() != null)
                        if (amount < Integer.parseInt(product.getProDetails().get(0).getCount()))
                            add.setEnabled(true);*/
                        }
                    });
                }
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) linear.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                linear.setLayoutParams(params);

                selected.setChecked(!cartItem.isNotChecked());
                selected.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (compoundButton.isPressed()) {
                        cartItem.setNotChecked(!b);
                        float price = Float.parseFloat(cartItem.getPrice()) * Float.parseFloat(cartItem.getQuantity());
                        changeTotal(cartData.getTotal() + (b ? price : (-1 * price)));
                    }
                });
            } else {
                totalLayout.setVisibility(View.VISIBLE);
                itemLayout.setVisibility(View.GONE);
                total.setText(String.format(Locale.getDefault(), "%.2f", cartData.getTotal()));
            }
        }
    }
}
