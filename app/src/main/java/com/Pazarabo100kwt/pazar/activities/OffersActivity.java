package com.Pazarabo100kwt.pazar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.adapters.ProductsAdapter;
import com.Pazarabo100kwt.pazar.baseactivity.BaseActivity;
import com.Pazarabo100kwt.pazar.retrofit.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.retrofit.RetrofitModel;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.search_products.Product;
import com.Pazarabo100kwt.pazar.models.search_products.SearchProductResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class OffersActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ProductsAdapter adapter;
    List<Product> productList;
    int page = 1, maxPage;
    boolean reachBottom;
    private boolean isRefresh = true;
    private HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);
        params = new HashMap<>();
        toolbar.setTitle(getString(R.string.offers));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        productList = new ArrayList<>();
        adapter = new ProductsAdapter(this, productList, progress);
        recycler.setAdapter(adapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !reachBottom && page < maxPage) {
                    reachBottom = true;
                    page++;
                    getOffers();
                }
            }
        });
        getOffers();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void getOffers() {
        progress.setVisibility(View.VISIBLE);
        params.put(StaticMembers.OFFERS, "1");
        Call<SearchProductResponse> call = RetrofitModel.getApi(getBaseContext()).getProducts(params);
        call.enqueue(new CallbackRetrofit<SearchProductResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<SearchProductResponse> call, @NotNull Response<SearchProductResponse> response) {
                progress.setVisibility(View.GONE);
                reachBottom = false;
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    maxPage = response.body().getData().getLastPage();
                    if (isRefresh)
                        productList.clear();
                    isRefresh = false;
                    productList.addAll(response.body().getData().getData());
                    adapter.notifyDataSetChanged();
                    if (productList.isEmpty())
                        recycler.setVisibility(View.GONE);
                    else recycler.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SearchProductResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });
    }
}
