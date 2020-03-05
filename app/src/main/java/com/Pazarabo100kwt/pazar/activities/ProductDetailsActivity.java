package com.Pazarabo100kwt.pazar.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.Pazarabo100kwt.pazar.BuildConfig;
import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.adapters.ProductImagesAdapter;
import com.Pazarabo100kwt.pazar.baseactivity.BaseActivity;
import com.Pazarabo100kwt.pazar.fragments.ImageFragment;
import com.Pazarabo100kwt.pazar.fragments.VideoFragment;
import com.Pazarabo100kwt.pazar.helpers.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.helpers.RetrofitModel;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.cart.AddCartResponse;
import com.Pazarabo100kwt.pazar.models.search_products.MeasureItem;
import com.Pazarabo100kwt.pazar.models.search_products.ProDetails;
import com.Pazarabo100kwt.pazar.models.search_products.Product;
import com.Pazarabo100kwt.pazar.models.wishlist_models.ErrorWishListResponse;
import com.Pazarabo100kwt.pazar.models.wishlist_models.WishlistResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProductDetailsActivity extends BaseActivity {

    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.remove)
    ImageButton remove;
    @BindView(R.id.add)
    ImageButton add;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.addToCart)
    CardView addToCart;
    @BindView(R.id.addToCartText)
    TextView addToCartText;
    @BindView(R.id.buyNow)
    CardView buyNow;
    @BindView(R.id.buyNowText)
    TextView buyNowText;
    @BindView(R.id.productId)
    TextView productId;
    @BindView(R.id.amount)
    TextView amountText;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.reviewNo)
    TextView reviewNo;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.priceOld)
    TextView priceOld;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.subCode)
    TextView subCode;
    @BindView(R.id.sizesColor)
    TextView sizesColor;
    @BindView(R.id.selectColor)
    TextView selectColor;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.colorsTabLayout)
    TabLayout colorsTabLayout;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.favorite)
    CheckBox favorite;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int amount = 1, maxAmount = 1;
    Product product;
    ProductImagesAdapter adapter;
    VideoFragment videoFragment;
    ProDetails proDetails;
    String actualPrice;
    @BindView(R.id.shareproduct)
    ImageView shareproduct;
    @BindView(R.id.botomLayout)
    LinearLayout botomLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        product = (Product) getIntent().getSerializableExtra(StaticMembers.PRODUCT);
        adapter = new ProductImagesAdapter(getSupportFragmentManager());
        if (product.getVideo() != null) {
            videoFragment = VideoFragment.getInstance(product.getVideo());
            adapter.addFragment(videoFragment);
        }
        for (String s : product.getPhotos()) {
            ImageFragment imageFragment = ImageFragment.getInstance(s, product.getPhotos(), pager, this);
            adapter.addFragment(imageFragment);
        }
        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        name.setText(product.getName());
        toolbar.setTitle(product.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        productId.setText(product.getProductNo());

        description.setText(product.getDetails());
        favorite.setChecked(product.isWishlist());
        favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                changeFavorite();
            }
        });
        ratingBar.setRating(product.getRate());
        reviewNo.setText(String.format(Locale.getDefault(), getString(R.string.d_reviewers), 0));
        StaticMembers.changeDots(0, adapter.getCount(), indicator, getBaseContext());


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                StaticMembers.changeDots(position, adapter.getCount(), indicator, getBaseContext());
                if (videoFragment != null)
                    if (position != 0)
                        videoFragment.pauseVideo();
                //  else videoFragment.resumeVideo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (savedInstanceState != null) {
            amount = savedInstanceState.getInt(StaticMembers.AMOUNT);
            maxAmount = savedInstanceState.getInt(StaticMembers.MAX_AMOUNT);
        }
        if (product.getProDetails() != null) {
            View view = null;
            for (ProDetails details : product.getProDetails()) {
                TabLayout.Tab tab = colorsTabLayout.newTab();
                view = LayoutInflater.from(this).inflate(R.layout.item_color_tab, null, false);
                TextView textView = view.findViewById(R.id.text);
                CardView cardView = view.findViewById(R.id.colorCard);
                if (details.getColor() != null) {
                    textView.setText(details.getColor().getCode() + "");
                    if (details.getColor().getHastag().isEmpty()) {
                        colorsTabLayout.setVisibility(View.GONE);
                        selectColor.setVisibility(View.GONE);
                    } else
                        cardView.setCardBackgroundColor(Color.parseColor("#" + details.getColor().getHastag()));
                    tab.setCustomView(view);
                    colorsTabLayout.addTab(tab);
                }

            }
            if (view != null) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                ViewGroup.LayoutParams params1 = colorsTabLayout.getLayoutParams();
                params1.height = params.height;
                colorsTabLayout.setLayoutParams(params1);
            }


            colorsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    changeViewsOnSelection();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        changeViewsOnSelection();

        add.setOnClickListener(v ->
        {
            amount++;
            amountText.setText(String.format(Locale.getDefault(), "%d", amount));
            if (amount > 1)
                remove.setEnabled(true);
//            if (product.getProDetails().get(0).getCount() != null){
                if (amount >= maxAmount){
                    add.setEnabled(false);
                }

            if (actualPrice != null && !actualPrice.isEmpty()) {
                float total = amount * Float.parseFloat(actualPrice);
                addToCartText.setText(String.format(Locale.getDefault(), getString(R.string.add_to_cart_s), total));
                buyNowText.setText(String.format(Locale.getDefault(), getString(R.string.buy_now_s), total));
            }
        });
        remove.setOnClickListener(v ->
        {
            if (amount < 2){
                remove.setEnabled(false);
            }else {
                amount--;
                amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                if (amount < 2)
                    remove.setEnabled(false);
//                if (product.getProDetails().get(0).getCount() != null)
                    if (amount < maxAmount){
                        add.setEnabled(true);
                    }
                if (actualPrice != null && !actualPrice.isEmpty()) {
                    float total = amount * Float.parseFloat(actualPrice);
                    addToCartText.setText(String.format(Locale.getDefault(), getString(R.string.add_to_cart_s), total));
                    buyNowText.setText(String.format(Locale.getDefault(), getString(R.string.buy_now_s), total));
                }
            }
        });
        addToCart.setOnClickListener(v -> changeCartItem(false));
        buyNow.setOnClickListener(v -> changeCartItem(true));
    }

    int selectedSizeTab = 0;

    void changeViewsOnSelection() {
        proDetails = product.getProDetails().get(colorsTabLayout.getSelectedTabPosition());
        if (proDetails.getMeasure() == null) {
            tabLayout.setVisibility(View.GONE);
            sizesColor.setVisibility(View.GONE);

        } else if (proDetails.getMeasure().isEmpty()) {
            tabLayout.setVisibility(View.GONE);
            sizesColor.setVisibility(View.GONE);
        } else {

           /* if (proDetails.getSubcode() != null && !proDetails.getSubcode().isEmpty() &&
                 !proDetails.getSubcode().equals("0") &&
                 !proDetails.getSubcode().isEmpty()) {
                subCode.setVisibility(View.VISIBLE);
                subCode.setText(String.format(Locale.getDefault(), getString(R.string.subcode_s), proDetails.getSubcode()));
            } else {
//                subCode.setVisibility(View.GONE);
            }*/


            if (proDetails.getMeasure().get(0).getId()==29) {
                tabLayout.setVisibility(View.GONE);
                sizesColor.setVisibility(View.GONE);
            } else {
                selectedSizeTab = tabLayout.getSelectedTabPosition();
                tabLayout.removeAllTabs();
                for (MeasureItem measure : proDetails.getMeasure()) {
                    TabLayout.Tab tab = tabLayout.newTab();
                    tab.setText(Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.tab_text_unselected_line),
                            measure.getName())));
                    tabLayout.addTab(tab);
                }

                TabLayout.Tab tab = tabLayout.getTabAt(selectedSizeTab < tabLayout.getTabCount() && selectedSizeTab > -1 ? selectedSizeTab : 0);
                if (tab != null) {
                    tab.setText(Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.tab_text_selected_line),
                            proDetails.getMeasure().get(selectedSizeTab < tabLayout.getTabCount() && selectedSizeTab > -1 ? selectedSizeTab : 0).getName())));
                    tab.select();
                }
                if (proDetails.getMeasure().size() > 3)
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                else tabLayout.setTabMode(TabLayout.MODE_FIXED);

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (proDetails.getMeasure() != null && proDetails.getMeasure().size() > tab.getPosition()) {
                            tab.setText(Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.tab_text_selected_line),
                                    proDetails.getMeasure().get(tab.getPosition()).getName())));
                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        if (proDetails.getMeasure() != null && proDetails.getMeasure().size() > tab.getPosition())
                            tab.setText(Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.tab_text_unselected_line),
                                    proDetails.getMeasure().get(tab.getPosition()).getName())));
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

            }



        }


        if (proDetails.getCount() != null) {
            maxAmount = Integer.parseInt(proDetails.getCount());
            if (maxAmount==1){
                add.setEnabled(false);
                remove.setEnabled(false);
            }
            if (amount > maxAmount)
                amount = 1;
        }
        if (proDetails.getPrice() != null) {
            priceOld.setText(String.format(Locale.getDefault(), getString(R.string.s_kwd), proDetails.getPrice()));
            priceOld.setPaintFlags(priceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            price.setText(String.format(Locale.getDefault(), getString(R.string.s_kwd), proDetails.getPrice()));
            actualPrice = proDetails.getPrice();
        }


        if (proDetails.getNewprice() != null) {
            price.setText(String.format(Locale.getDefault(), getString(R.string.s_kwd), proDetails.getNewprice()));
            priceOld.setVisibility(View.VISIBLE);
            actualPrice = proDetails.getNewprice();
        } else priceOld.setVisibility(View.GONE);
        if (actualPrice != null) {
            addToCartText.setText(String.format(Locale.getDefault(), getString(R.string.add_to_cart_s), Float.parseFloat(actualPrice) * amount));
            buyNowText.setText(String.format(Locale.getDefault(), getString(R.string.buy_now_s), amount * Float.parseFloat(actualPrice)));
            amountText.setText(String.format(Locale.getDefault(), "%d", amount));
        }

        if (amount < 2)
            remove.setEnabled(false);
        if (proDetails.getCount() != null)
            if (amount > Integer.parseInt(proDetails.getCount()))
                add.setEnabled(false);
            else
                add.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.home:
                StaticMembers.startActivityOverAll(this, MainActivity.class);
                break;
            case R.id.favorite:
                startActivity(new Intent(this, FavoritesActivity.class));
                break;
            case R.id.offers:
//                MessageFragment.getInstance().show(getSupportFragmentManager(), getString(R.string.messages));
                startActivity(new Intent(this, OffersActivity.class));
                break;
        }
        return true;
    }

    private void changeCartItem(boolean buyNow) {
        progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            Intent intent = new Intent(getBaseContext(), LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            startActivity(intent);
        } else {

            Call<AddCartResponse> call;
            if (proDetails.getMeasure() != null && !proDetails.getMeasure().isEmpty())
                call = RetrofitModel.getApi(this).addOrEditCart("" + product.getId(), amount,
                        proDetails.getMeasure().get(tabLayout.getSelectedTabPosition()).getId(), proDetails.getColor().getId());
            else
                call = RetrofitModel.getApi(this).addOrEditCart("" + product.getId(), amount,
                        proDetails.getColor().getId());

            call.enqueue(new CallbackRetrofit<AddCartResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<AddCartResponse> call, @NotNull Response<AddCartResponse> response) {
                    progress.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount - 1));
                        StaticMembers.checkLoginRequired(response.errorBody(), getBaseContext());

                    } else if (response.body() != null) {
                        StaticMembers.toastMessageSuccess(getBaseContext(), response.body().getMessage());
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                        if (buyNow)
                            startActivity(new Intent(getBaseContext(), CartActivity.class));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(StaticMembers.AMOUNT, amount);
        outState.putInt(StaticMembers.MAX_AMOUNT, maxAmount);
    }

    private void changeFavorite() {
        progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            StaticMembers.openLogin(this);
        } else {
            Call<WishlistResponse> call = RetrofitModel.getApi(this).toggleWishlist(product.getId());
            call.enqueue(new CallbackRetrofit<WishlistResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<WishlistResponse> call, @NotNull Response<WishlistResponse> response) {
                    progress.setVisibility(View.GONE);
                    WishlistResponse result = response.body();
                    if (response.isSuccessful() && result != null) {
                        StaticMembers.toastMessageSuccess(getBaseContext(), result.getMessage());
                    } else {
                        favorite.setChecked(!favorite.isChecked());
                        try {
                            ErrorWishListResponse errorLoginResponse = null;
                            ResponseBody errBody = response.errorBody();
                            String s = errBody.string();
                            StaticMembers.checkLoginRequired(response.errorBody(), ProductDetailsActivity.this);
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(s, ErrorWishListResponse.class);
                                if (errorLoginResponse != null) {
                                    StaticMembers.toastMessageFailed(getBaseContext(), errorLoginResponse.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            StaticMembers.toastMessageFailed(getBaseContext(), getString(R.string.connection_error));
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

    @OnClick(R.id.shareproduct)
    public void onViewClicked() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Pazar");
            String shareMessage= getString(R.string.Let_me_recommend_this_app)+"\n";
            shareMessage = shareMessage +" "+ "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }




}
