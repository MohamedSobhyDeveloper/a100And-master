package com.magdy.abo100.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.magdy.abo100.R;
import com.magdy.abo100.adapters.ProductsAdapter;
import com.magdy.abo100.baseactivity.BaseActivity;
import com.magdy.abo100.fragments.MessageFragment;
import com.magdy.abo100.helpers.CallbackRetrofit;
import com.magdy.abo100.helpers.RetrofitModel;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.categories.ChildsItem;
import com.magdy.abo100.models.search_products.Product;
import com.magdy.abo100.models.search_products.SearchProductResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SearchProductsActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.back)
    ImageButton back;

    ProductsAdapter adapter;
    List<Product> productList;
    int page = 1, maxPage;
    boolean reachBottom;
    String searchString, catId, subCatID;
    com.magdy.abo100.models.categories.DataItem category;
    ChildsItem subCategory;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        ButterKnife.bind(this);
        params = new HashMap<>();
        searchString = getIntent().getStringExtra(StaticMembers.SEARCH);
        catId = getIntent().getStringExtra(StaticMembers.CATEGORY_ID);
        subCatID = getIntent().getStringExtra(StaticMembers.SUB_CATEGORY_ID);
        category = (com.magdy.abo100.models.categories.DataItem) getIntent().getSerializableExtra(StaticMembers.CATEGORY);
        subCategory = (ChildsItem) getIntent().getSerializableExtra(StaticMembers.SUB_CATEGORY);
        if (category != null && subCategory != null)
            toolbar.setTitle(String.format(Locale.getDefault(), "%s | %s", category.getName(), subCategory.getName()));
        else if (searchString != null)
            toolbar.setTitle(searchString);
        else toolbar.setTitle(R.string.products);
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
                    getProducts();
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
            getProducts();
            onBackPressed();
        });
        getProducts();
    }

    HashMap<String, String> params;

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
            case R.id.messages:
                MessageFragment.getInstance().show(getSupportFragmentManager(), getString(R.string.messages));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
        else
            super.onBackPressed();

    }

    void getProducts() {
        progress.setVisibility(View.VISIBLE);

        if (searchString != null && !searchString.isEmpty())
            params.put(StaticMembers.SEARCH, searchString);
        if (category != null)
            params.put(StaticMembers.CATEGORY + "_id", "" + category.getId());
        if (subCategory != null)
            params.put(StaticMembers.SUB_CATEGORY + "_id", "" + subCategory.getId());
        if (subCatID != null)
            params.put(StaticMembers.SUB_CATEGORY_ID, subCatID);
        if (catId != null)
            params.put(StaticMembers.CATEGORY_ID, catId);
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
