package com.app.pazar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pazar.R;
import com.app.pazar.adapters.GiftAdapter;
import com.app.pazar.baseactivity.BaseActivity;
import com.app.pazar.fragments.MessageFragment;
import com.app.pazar.helpers.CallbackRetrofit;
import com.app.pazar.helpers.RetrofitModel;
import com.app.pazar.helpers.StaticMembers;
import com.app.pazar.models.gifts_models.DataItem;
import com.app.pazar.models.gifts_models.GiftsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class GiftsActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    GiftAdapter adapter;
    List<DataItem> productList;
    int page = 1, maxPage;
    boolean reachBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.gifts);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        productList = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GiftAdapter(this, productList);
        recycler.setAdapter(adapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !reachBottom && page < maxPage) {
                    reachBottom = true;
                    page++;
                    getGifts();
                }
            }
        });
        getGifts();
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

    void getGifts() {
        progress.setVisibility(View.VISIBLE);
        Call<GiftsResponse> call = RetrofitModel.getApi(getBaseContext()).getGifts();
        call.enqueue(new CallbackRetrofit<GiftsResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<GiftsResponse> call, @NotNull Response<GiftsResponse> response) {
                progress.setVisibility(View.GONE);
                reachBottom = false;
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    productList.clear();
                    productList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    if (productList.isEmpty())
                        recycler.setVisibility(View.GONE);
                    else recycler.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<GiftsResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });
    }
}
