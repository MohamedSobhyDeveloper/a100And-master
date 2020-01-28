package com.app.pazar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pazar.R;
import com.app.pazar.helpers.CallbackRetrofit;
import com.app.pazar.helpers.RetrofitModel;
import com.app.pazar.helpers.SliderHelper;
import com.app.pazar.models.slider_models.Data;
import com.app.pazar.models.slider_models.SliderItem;
import com.app.pazar.models.slider_models.SliderResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SlidersFragment extends Fragment {
    private View view;

    public static SlidersFragment getInstance() {
        SlidersFragment fragment = new SlidersFragment();
        return fragment;
    }

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    RelativeLayout progress;
    List<ArrayList<SliderItem>> sliderList;
    SliderHelper sliderHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        this.view = view;
        sliderList = new ArrayList<>();
        getSliders();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sliderHelper!=null)
        {
            sliderHelper.pauseAll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sliderHelper!=null)
        {
            sliderHelper.resumeAll();
        }
    }

    void getSliders() {
        progress.setVisibility(View.VISIBLE);
        Call<SliderResponse> call = RetrofitModel.getApi(getContext()).getSlider();
        call.enqueue(new CallbackRetrofit<SliderResponse>(getActivity()) {
            @Override
            public void onResponse(@NotNull Call<SliderResponse> call, @NotNull Response<SliderResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().isStatus()) {
                        Data data = response.body().getData();
                        ArrayList<SliderItem> list;
                        if (data.getSlider1() != null) {
                            list = new ArrayList<>(data.getSlider1());
                            sliderList.add(list);
                        }
                        if (data.getSlider2() != null) {
                            list = new ArrayList<>(data.getSlider2());
                            sliderList.add(list);
                        }
                        if (data.getSlider3() != null) {
                            list = new ArrayList<>(data.getSlider3());
                            sliderList.add(list);
                        }

                        if (getActivity() != null) {
                            sliderHelper = new SliderHelper(Objects.requireNonNull(getActivity()), view, sliderList);
                            sliderHelper.notifyAdapters();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SliderResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });

    }
}
