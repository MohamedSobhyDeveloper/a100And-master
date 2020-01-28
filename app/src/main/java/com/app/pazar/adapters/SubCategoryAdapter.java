package com.app.pazar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.app.pazar.R;
import com.app.pazar.activities.SearchProductsActivity;
import com.app.pazar.helpers.StaticMembers;
import com.app.pazar.models.categories.ChildsItem;
import com.app.pazar.models.categories.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.Holder> {
    private Context context;
    private List<ChildsItem> list;
    private DataItem category;

    public SubCategoryAdapter(Context context, List<ChildsItem> list, DataItem category) {
        this.context = context;
        this.list = list;
        this.category = category;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ChildsItem subCategory = list.get(position);
        holder.name.setText(subCategory.getName());
        Glide.with(context).load(subCategory.getImageIcon()).into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SearchProductsActivity.class);
            intent.putExtra(StaticMembers.CATEGORY, category);
            intent.putExtra(StaticMembers.SUB_CATEGORY, subCategory);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
