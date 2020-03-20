package com.Pazarabo100kwt.pazar.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.adapters.CartAdapter;
import com.Pazarabo100kwt.pazar.baseactivity.BaseActivity;
import com.Pazarabo100kwt.pazar.retrofit.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.helpers.RecyclerItemTouchHelper;
import com.Pazarabo100kwt.pazar.retrofit.RetrofitModel;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.cart.CartResponse;
import com.Pazarabo100kwt.pazar.models.cart.Data;
import com.Pazarabo100kwt.pazar.models.message_models.MessageResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.Pazarabo100kwt.pazar.helpers.StaticMembers.openLogin;

public class CartActivity extends BaseActivity {
    private static final int ORDER_REQ = 456;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.addPromo)
    CardView addPromo;
    @BindView(R.id.order)
    CardView order;
    CartAdapter adapter;
    Data cartData;
    @BindView(R.id.invoicelimit)
    TextView invoicelimit;
    @BindView(R.id.relativeView)
    RelativeLayout relativeView;
    @BindView(R.id.isemptyTv)
    TextView isemptyTv;
    String promocode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        promocode="";

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        cartData = new Data();
        cartData.setCart(new ArrayList<>());
        adapter = new CartAdapter(this, cartData, progress,promocode);
        recycler.setAdapter(adapter);

        getCart(promocode);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.START,
                (viewHolder, direction, position) -> {
                    if (cartData.getCart().size() > position) adapter.deleteCartItem(position);
                });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler);
        order.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SelectPaymentActivity.class);

            intent.putExtra(StaticMembers.CART, cartData);
            intent.putExtra(StaticMembers.promocode, promocode);

            startActivityForResult(intent, ORDER_REQ);


        });
        addPromo.setOnClickListener(v -> {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            alertDialog.setTitle(getString(R.string.add_promocode));
            input.setHint(R.string.add_promocode);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input); // uncomment this line
            alertDialog.setPositiveButton(getString(R.string.ok), (dialog, which) -> setPromo(input.getText().toString()));
            alertDialog.setNegativeButton(getString(R.string.cancel), null);
            alertDialog.show();
        });


    }


    public void setPromo(String s) {

//        progress.setVisibility(View.VISIBLE);

        promocode=s;
        getCart(promocode);


//        Call<MessageResponse> call = RetrofitModel.getApi(this).addPromo(s);
//        call.enqueue(new CallbackRetrofit<MessageResponse>(this) {
//            @Override
//            public void onResponse(@NotNull Call<MessageResponse> call, @NotNull Response<MessageResponse> response) {
//                progress.setVisibility(View.GONE);
//                MessageResponse result;
//                if (response.isSuccessful()) {
//                    result = response.body();
//                    if (result != null) {
//                        StaticMembers.toastMessageSuccess(getBaseContext(), result.getMessage());
//                        if (result.isStatus()) {
//                            getCart("");
//                        }
//                    }
//                } else {
//                    ResponseBody errorBody = response.errorBody();
//                    try {
//                        if (errorBody != null) {
//                            String e = errorBody.string();
//                            StaticMembers.checkLoginRequired(errorBody, CartActivity.this);
//                            result = new Gson().fromJson(e, MessageResponse.class);
//                            if (result != null && result.getMessage() != null)
//                                StaticMembers.toastMessageFailed(getBaseContext(), result.getMessage());
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {
//                super.onFailure(call, t);
//                progress.setVisibility(View.GONE);
//            }
//        });
//
//


    }

    public void getCart(String code) {
        progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            openLogin(this);
            finish();
        } else {
            Call<CartResponse> call;
            if (!code.isEmpty()){
                 call = RetrofitModel.getApi(getBaseContext()).getCart(code);

            }else {
                call = RetrofitModel.getApi(getBaseContext()).getCart();

            }
            call.enqueue(new CallbackRetrofit<CartResponse>(this) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NotNull Call<CartResponse> call, @NotNull Response<CartResponse> response) {
                    progress.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        cartData = response.body().getData();

                        if (cartData.getCart()!=null&&cartData.getCart().size()>0){

                            double limited = Double.parseDouble(cartData.getCart().get(0).getInvoicelimit());

                            double total=cartData.getTotal()-Integer.parseInt(cartData.getCart().get(0).getDeliverycharge());
                            if (total>= limited) {
                                order.setVisibility(View.VISIBLE);
                                invoicelimit.setVisibility(View.GONE);
                            } else {
                                order.setVisibility(View.GONE);
                                invoicelimit.setVisibility(View.VISIBLE);
                                invoicelimit.setText(getString(R.string.Minimum_payment_to_be_completed) + " " + limited+" "+getString(R.string.kd));
                            }
                            adapter.setCartData(cartData,promocode);
                            adapter.notifyDataSetChanged();

                        }else {

                            relativeView.setVisibility(View.GONE);
                            isemptyTv.setVisibility(View.VISIBLE);

                        }


                    } else {
                        promocode="";
                        StaticMembers.checkLoginRequired(response.errorBody(), CartActivity.this);

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ORDER_REQ) {
            if (resultCode == RESULT_OK) {
                getCart(promocode);
            }
        }
    }
}
