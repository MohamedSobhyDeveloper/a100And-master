package com.Pazarabo100kwt.pazar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.Pazarabo100kwt.pazar.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterFragment extends DialogFragment {
    OnChooseListener listener;
    String strDef;

    public static FilterFragment getInstance(String s, OnChooseListener listener) {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.listener = listener;
        filterFragment.strDef = s;
        return filterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);
       /* Toolbar actionBar = v.findViewById(R.id.toolbarFilter);
        if (actionBar != null) {
            actionBar.setNavigationOnClickListener(v1 -> getDialog().dismiss());
        }*/
        return v;
    }

    @BindView(R.id.group)
    RadioGroup group;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (strDef != null)
            if (strDef.equals("price asc")) {
                ((RadioButton) group.findViewById(R.id.lowPrice)).setChecked(true);
            } else if (strDef.equals("price desc")) {
                ((RadioButton) group.findViewById(R.id.highPrice)).setChecked(true);
            } else if (strDef.equals("date desc")) {
                ((RadioButton) group.findViewById(R.id.newest)).setChecked(true);
            } else if (strDef.equals("date asc")) {
                ((RadioButton) group.findViewById(R.id.oldest)).setChecked(true);

            }
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            switch (checkedId) {
                case R.id.lowPrice:
                    listener.onChoose("price asc");
                    break;
                case R.id.highPrice:
                    listener.onChoose("price desc");
                    break;
                case R.id.newest:
                    listener.onChoose("date desc");
                    break;
                case R.id.oldest:
                    listener.onChoose("date asc");
                    break;
            }
            dismiss();
        });
    }

    public interface OnChooseListener {
        public void onChoose(String s);
    }
}
