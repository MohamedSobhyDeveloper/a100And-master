package com.magdy.abo100.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.magdy.abo100.R;
import com.magdy.abo100.adapters.CategoryAdapter;
import com.magdy.abo100.baseactivity.BaseActivity;
import com.magdy.abo100.fragments.MessageFragment;
import com.magdy.abo100.helpers.CallbackRetrofit;
import com.magdy.abo100.helpers.RetrofitModel;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.categories.CategoriesResponse;
import com.magdy.abo100.models.categories.DataItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CategoriesActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search)
    TextInputEditText search;
    CategoryAdapter adapter;
    List<DataItem> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(getBaseContext(), SearchProductsActivity.class);
                intent.putExtra(StaticMembers.SEARCH, search.getText().toString());
                startActivity(intent);
                return true;
            }
            return false;
        });
        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(this, categoryList);
        recycler.setAdapter(adapter);
        getCategories();
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

    void getCategories() {
        progress.setVisibility(View.VISIBLE);
        Call<CategoriesResponse> call = RetrofitModel.getApi(this).getCategories();
        call.enqueue(new CallbackRetrofit<CategoriesResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<CategoriesResponse> call, @NotNull Response<CategoriesResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    categoryList.clear();
                    categoryList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    if (categoryList.isEmpty())
                        recycler.setVisibility(View.GONE);
                    else recycler.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoriesResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });

    }
}
