package com.app.pazar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pazar.R;
import com.app.pazar.adapters.DivAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DivisionsFragment extends Fragment {
    public static DivisionsFragment getInstance() {
        DivisionsFragment fragment = new DivisionsFragment();
        return fragment;
    }

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    List<Integer> divisionList;
    DivAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_divs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        divisionList = new ArrayList<>();
        divisionList.add(R.drawable.pins);
        divisionList.add(R.drawable.pallons);
        divisionList.add(R.drawable.colors_pallete);
        divisionList.add(R.drawable.toy);
        divisionList.add(R.drawable.pins);
        divisionList.add(R.drawable.pallons);
        divisionList.add(R.drawable.colors_pallete);
        divisionList.add(R.drawable.toy);
        divisionList.add(R.drawable.pins);
        divisionList.add(R.drawable.pallons);
        divisionList.add(R.drawable.colors_pallete);
        divisionList.add(R.drawable.toy);
        adapter = new DivAdapter(getContext(), divisionList);
        recyclerView.setAdapter(adapter);
    }
}
