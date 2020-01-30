package com.Pazarabo100kwt.pazar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.adapters.FavoritesAdapter;
import com.Pazarabo100kwt.pazar.baseactivity.BaseActivity;
import com.Pazarabo100kwt.pazar.fragments.MessageFragment;
import com.Pazarabo100kwt.pazar.helpers.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.helpers.RetrofitModel;
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

public class FavoritesActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.group)
    RadioGroup group;

    FavoritesAdapter adapter;
    List<Product> productList;
    int page = 1, maxPage;
    boolean reachBottom;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        ButterKnife.bind(this);
        params = new HashMap<>();
        toolbar.setTitle(getString(R.string.favorite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        productList = new ArrayList<>();
        adapter = new FavoritesAdapter(this, productList, progress, recycler);
        recycler.setAdapter(adapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !reachBottom && page < maxPage) {
                    reachBottom = true;
                    page++;
                    getWishList();
                }
            }
        });
        back.setOnClickListener(v -> onBackPressed());
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            String s = null;
            switch (checkedId) {
                case R.id.lowPrice:
                    s = "price asc";
                    break;
                case R.id.highPrice:
                    s = "price desc";
                    break;
                case R.id.newest:
                    s = "date desc";
                    break;
                case R.id.oldest:
                    s = "date asc";
                    break;
            }
            if (s != null)
                params.put(StaticMembers.SORT, s);
            isRefresh = true;
            getWishList();
            onBackPressed();
        });
        getWishList();
    }

    HashMap<String, String> params;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
        else
            super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_default, menu);
        menu.findItem(R.id.filter).setVisible(true);
        menu.findItem(R.id.cart).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                drawer.openDrawer(GravityCompat.END);
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

    void getWishList() {
        progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            StaticMembers.openLogin(this);
        } else {
            Call<SearchProductResponse> call = RetrofitModel.getApi(getBaseContext()).getWishList(params);
            call.enqueue(new CallbackRetrofit<SearchProductResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<SearchProductResponse> call, @NotNull Response<SearchProductResponse> response) {
                    progress.setVisibility(View.GONE);
                    reachBottom = false;
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        maxPage = response.body().getData().getLastPage();
                        if (isRefresh)
                            productList.clear();
                        productList.addAll(response.body().getData().getData());
                        adapter.notifyDataSetChanged();
                        if (productList.isEmpty())
                            recycler.setVisibility(View.GONE);
                        else recycler.setVisibility(View.VISIBLE);
                    } else {
                        StaticMembers.checkLoginRequired(response.errorBody(), FavoritesActivity.this);
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
}
