package com.Pazarabo100kwt.pazar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.adapters.SubCategoryAdapter;
import com.Pazarabo100kwt.pazar.baseactivity.BaseActivity;
import com.Pazarabo100kwt.pazar.fragments.MessageFragment;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.categories.ChildsItem;
import com.Pazarabo100kwt.pazar.models.categories.DataItem;

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
