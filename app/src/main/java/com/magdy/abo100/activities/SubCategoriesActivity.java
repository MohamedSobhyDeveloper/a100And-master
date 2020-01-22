package com.magdy.abo100.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.magdy.abo100.R;
import com.magdy.abo100.adapters.SubCategoryAdapter;
import com.magdy.abo100.baseactivity.BaseActivity;
import com.magdy.abo100.fragments.MessageFragment;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.categories.ChildsItem;
import com.magdy.abo100.models.categories.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoriesActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    SubCategoryAdapter adapter;
    List<ChildsItem> categoryList;
    DataItem category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        ButterKnife.bind(this);
        category = (DataItem) getIntent().getSerializableExtra(StaticMembers.CATEGORY);
        toolbar.setTitle(category.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        categoryList = category.getChilds();
        adapter = new SubCategoryAdapter(this, categoryList, category);
        recycler.setAdapter(adapter);
        if (categoryList.isEmpty())
            recycler.setVisibility(View.GONE);
        else recycler.setVisibility(View.VISIBLE);
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
}
