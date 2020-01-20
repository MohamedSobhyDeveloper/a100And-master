package com.magdy.abo100.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.magdy.abo100.R;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.gifts_models.DataItem;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.Holder> {
    private Context context;
    private List<DataItem> list;

    public GiftAdapter(Context context, List<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_gift, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem item = list.get(position);
        holder.discount.setText(String.format(Locale.getDefault(), context.getString(R.string.dis_s_kwd ), item.getAmount()));
        holder.code.setText(item.getCode());
        holder.cardCode.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(context.getString(R.string.promo_code), item.getCode());
            assert clipboard != null;
            clipboard.setPrimaryClip(clip);
            StaticMembers.toastMessageShort(context, R.string.done_copied);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.cardCode)
        CardView cardCode;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
