package com.Pazarabo100kwt.pazar.adapters;

import android.annotation.SuppressLint;
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

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.activities.CartActivity;
import com.Pazarabo100kwt.pazar.activities.LogInActivity;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.cart.AddCartResponse;
import com.Pazarabo100kwt.pazar.models.cart.CartItem;
import com.Pazarabo100kwt.pazar.models.cart.Data;
import com.Pazarabo100kwt.pazar.models.cart.Product;
import com.Pazarabo100kwt.pazar.models.cart.delete_cart_models.DeleteCartResponse;
import com.Pazarabo100kwt.pazar.retrofit.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.retrofit.RetrofitModel;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {



    private CartActivity activity;
    private RelativeLayout progress;
    private Data cartData;
    private String promocode;
    private checkTotalInterface checkTotalInterface;

    public CartAdapter(CartActivity activity, Data cartData, RelativeLayout progress, String promocode, checkTotalInterface checkTotalInterface) {
        this.activity = activity;
        this.cartData = cartData;
        this.progress = progress;
        this.promocode = promocode;
        this.checkTotalInterface = checkTotalInterface;
    }

    void changeTotal(double total, double net) {
        cartData.setTotal(total);
        cartData.setNet(net);
        notifyItemChanged(cartData.getCart().size());
        checkTotalInterface.onClicktotal(total);
    }

    public void setCartData(Data cartData, String promocode) {
        this.cartData = cartData;
        this.promocode = promocode;
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

    private void changeCartItem(CartItem cartItem, int amount, TextView amountText,String status) {
        // progress.setVisibility(View.VISIBLE);
        Call<AddCartResponse> call = null;
        if (PrefManager.getInstance(activity).getAPIToken().isEmpty()) {
            Intent intent = new Intent(activity, LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            activity.startActivity(intent);
        } else {
            if (status.equals("")){
                if (cartItem.getUnit() != null && cartItem.getColor() != null) {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, cartItem.getUnit().getId(), cartItem.getColor().getId());
                } else if (cartItem.getUnit() != null) {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, cartItem.getUnit().getId());

                } else if (cartItem.getColor() != null) {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, cartItem.getColor().getId());
                } else {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, 0, 0);
                }
            }else {
                if (cartItem.getUnit() != null && cartItem.getColor() != null) {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, cartItem.getUnit().getId(), cartItem.getColor().getId(),status);
                } else if (cartItem.getUnit() != null) {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, cartItem.getUnit().getId(),status);

                } else if (cartItem.getColor() != null) {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, cartItem.getColor().getId(),status);
                } else {
                    call = RetrofitModel.getApi(activity).addOrEditCart(cartItem.getProductId(),
                            amount, 0, 0,status);
                }
            }



            call.enqueue(new CallbackRetrofit<AddCartResponse>(activity) {
                @Override
                public void onResponse(@NotNull Call<AddCartResponse> call, @NotNull Response<AddCartResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                        StaticMembers.checkLoginRequired(response.errorBody(), activity);
                    } else activity.getCart(promocode);
                }

                @Override
                public void onFailure(@NotNull Call<AddCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    //  progress.setVisibility(View.GONE);
                    activity.tryagain();
                }
            });
        }
    }

    public void deleteCartItem(int position) {
        CartItem cartItem = cartData.getCart().get(position);
        removeItem(position);
        // progress.setVisibility(View.VISIBLE);
        Call<DeleteCartResponse> call;
        if (PrefManager.getInstance(activity).getAPIToken().isEmpty()) {
            Intent intent = new Intent(activity, LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            activity.startActivity(intent);
        } else {
            HashMap hashMap = new HashMap();
            if (cartItem.getColor() != null&&cartItem.getUnit()!=null) {

                hashMap.put("colorcode", cartItem.getColor().getId() + "");
                hashMap.put("unitcode", cartItem.getUnit().getId() + "");
                call = RetrofitModel.getApi(activity).deleteCartItem(cartItem.getProductId(), hashMap);

            } else if (cartItem.getColor()!=null){
                hashMap.put("colorcode", cartItem.getColor().getId() + "");
                call = RetrofitModel.getApi(activity).deleteCartItem(cartItem.getProductId(), hashMap);
            }
                else {
                call = RetrofitModel.getApi(activity).deleteCartItem(cartItem.getProductId(), null);
            }

            call.enqueue(new CallbackRetrofit<DeleteCartResponse>(activity) {
                @Override
                public void onResponse(@NotNull Call<DeleteCartResponse> call, @NotNull Response<DeleteCartResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        restoreItem(cartItem, position);
                        if (!response.message().isEmpty())
                            StaticMembers.toastMessageSuccess(activity, response.message());
                        StaticMembers.checkLoginRequired(response.errorBody(), activity);
                    } else activity.getCart(promocode);
                }

                @Override
                public void onFailure(@NotNull Call<DeleteCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    // progress.setVisibility(View.GONE);
                    activity.tryagain();
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
        @BindView(R.id.productnumber)
        TextView productnumber;
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
        @BindView(R.id.delivery)
        TextView delivery;
        @BindView(R.id.deliveryLayout)
        LinearLayout deliveryLayout;
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.discountLayout)
        LinearLayout discountLayout;
        @BindView(R.id.productcolor)
        TextView productcolor;
        @BindView(R.id.productshape)
        TextView productshape;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void onBind() {
            int position = getAdapterPosition();
            if (position < cartData.getCart().size()) {

                totalLayout.setVisibility(View.GONE);
                deliveryLayout.setVisibility(View.GONE);
                discountLayout.setVisibility(View.GONE);
                itemLayout.setVisibility(View.VISIBLE);
                CartItem cartItem = cartData.getCart().get(position);
                Product product = cartItem.getProduct();

                if (product != null) {
                    if (product.getPhotos() != null)
                        Glide.with(activity).load(product.getPhotos().get(0)).into(image);

                    name.setText(product.getName());
                    productId.setText(String.format(Locale.getDefault(), activity.getString(R.string.product_id_s), product.getProductNo()));
                    if (cartData.getCart().get(position).getSubcode() != null && !cartData.getCart().get(position).getSubcode().equals("")) {
                        productnumber.setText(activity.getString(R.string.product_number) + " " + cartData.getCart().get(position).getSubcode() + "");
                        productnumber.setVisibility(View.VISIBLE);

                    } else {
                        productnumber.setVisibility(View.GONE);
                    }

                    if (cartData.getCart().get(position).getColor() != null) {

                        if (cartData.getCart().get(position).getColor().getHastag()!=null&&!cartData.getCart().get(position).getColor().getHastag().equals("")){
                            productcolor.setText(activity.getString(R.string.color) + " : " + cartData.getCart().get(position).getColor().getName() + "");
                            productcolor.setVisibility(View.VISIBLE);

                        } else {
                        productcolor.setVisibility(View.GONE);
                    }
                }

                    if (cartData.getCart().get(position).getUnit()!=null){
                        productshape.setText(activity.getString(R.string.shape) + " : " + cartData.getCart().get(position).getUnit().getName()+ "");
                        productshape.setVisibility(View.VISIBLE);
                    }else {
                        productshape.setVisibility(View.GONE);
                    }

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
                    
                    int maxamountv = 0;
                    for (int i=0;i<cartItem.getProduct().getProDetails().size();i++){
                        if (cartItem.getProduct().getProDetails().get(i).getColor().equals(cartItem.getColor().getId()+"")){
                             maxamountv= Integer.parseInt(cartItem.getProduct().getProDetails().get(i).getCount());

                        }
                    }

                    if (amount>=maxamountv){
                        add.setEnabled(false);
                    }else {
                        add.setEnabled(true);
                    }


                    add.setOnClickListener(v -> {
                        add.setEnabled(false);
                        remove.setEnabled(false);
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount + 1));
                        changeCartItem(cartItem,1, amountText,"");
                        if (amount + 1 > 1)
                            remove.setEnabled(true);
                        int maxamount= Integer.parseInt(cartItem.getProduct().getProDetails().get(0).getCount());

                        if (amount>=maxamount){
                            add.setEnabled(false);
                        }
               /* if (product.getProDetails().get(0).getCount() != null)
                    if (amount >= Integer.parseInt(product.getProDetails().get(0).getCount()))
                        add.setEnabled(false);*/
                    });
                    remove.setOnClickListener(v -> {
                        remove.setEnabled(false);
                        add.setEnabled(false);
                        if (amount < 2)
                            remove.setEnabled(false);
                        else {
                            amountText.setText(String.format(Locale.getDefault(), "%d", amount - 1));
                            changeCartItem(cartItem,  1, amountText,"1");
                            if (amount - 1 < 2)
                                remove.setEnabled(false);
                if (cartItem.getProduct().getProDetails().get(0).getCount() != null)
                        if (amount-1 < Integer.parseInt(product.getProDetails().get(0).getCount()))
                            add.setEnabled(true);
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
                        changeTotal(cartData.getTotal() + (b ? price : (-1 * price)), cartData.getNet() + (b ? price : (-1 * price)));
                    }
                });
            } else {
                totalLayout.setVisibility(View.VISIBLE);
                discountLayout.setVisibility(View.VISIBLE);
                itemLayout.setVisibility(View.GONE);
                discount.setText(cartData.getDiscount() + " " + "%");
                double discountvalue=cartData.getTotal()*cartData.getDiscount()/100;

                double totalvalue=cartData.getTotal()-discountvalue;

                total.setText(totalvalue + " " + activity.getString(R.string.kd));


                if (cartData.getCart() != null && cartData.getCart().size() > 0) {
                    deliveryLayout.setVisibility(View.VISIBLE);
                    delivery.setText(cartData.getCart().get(0).getDeliverycharge() + " " + activity.getString(R.string.kd));
                }

            }
        }
    }


    public interface checkTotalInterface {
        void onClicktotal(double total);
    }
}
