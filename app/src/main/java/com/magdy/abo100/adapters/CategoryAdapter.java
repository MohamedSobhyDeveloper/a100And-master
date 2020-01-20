package com.magdy.abo100.adapters;

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
import com.magdy.abo100.R;
import com.magdy.abo100.activities.SubCategoriesActivity;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.categories.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    private Context context;
    private List<DataItem> list;

    public CategoryAdapter(Context context, List<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem category = list.get(position);
        holder.name.setText(category.getName());
        Glide.with(context).load(category.getImageIcon()).into(holder.image);
//        Glide.with(context).load("https://scontent.fcai2-1.fna.fbcdn.net/v/t1.0-0/s261x260/66778498_2131988700260119_7003826027403149312_n.jpg?_nc_cat=107&_nc_oc=AQmqM5ZEybC-K2pL4IKxk-V0xUwLPIKWb8iTfXDIeFFZBk-CxMSlFDcTKgf0jpMCaQQ&_nc_ht=scontent.fcai2-1.fna&oh=f0f1545700fa2fc33467430399c4191c&oe=5DBBD5CB").into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra(StaticMembers.CATEGORY, category);
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
