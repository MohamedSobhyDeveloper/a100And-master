package com.Pazarabo100kwt.pazar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.activities.LogInActivity;
import com.Pazarabo100kwt.pazar.helpers.CallbackRetrofit;
import com.Pazarabo100kwt.pazar.helpers.PrefManager;
import com.Pazarabo100kwt.pazar.helpers.RetrofitModel;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.Pazarabo100kwt.pazar.models.message_models.MessageResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MessageFragment extends DialogFragment {

    public static MessageFragment getInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container, false);
        Toolbar actionBar = v.findViewById(R.id.toolbar);
        actionBar.setTitle(getString(R.string.message));
        if (actionBar != null) {
            actionBar.setNavigationOnClickListener(v1 -> getDialog().dismiss());
        }
        return v;
    }

    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.send)
    CardView send;
    @BindView(R.id.message)
    EditText message;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (PrefManager.getInstance(getContext()).getAPIToken().isEmpty()) {
            Intent intent = new Intent(getContext(), LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            startActivity(intent);
            dismiss();
        }
        send.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        progress.setVisibility(View.VISIBLE);
        Call<MessageResponse> call = RetrofitModel.getApi(getContext()).sendMessage(message.getText().toString());
        call.enqueue(new CallbackRetrofit<MessageResponse>(getContext()) {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call, @NotNull Response<MessageResponse> response) {
                progress.setVisibility(View.GONE);
                MessageResponse result;
                if (response.isSuccessful()) {
                    result = response.body();
                    if (result != null) {
                        StaticMembers.toastMessageSuccess(getContext(), result.getMessage());
                        if (result.isStatus()) {
                            dismiss();
                        }
                    }
                } else {

                    try {
                        ResponseBody errorBody = response.errorBody();
                        String e = errorBody.string();
                        StaticMembers.checkLoginRequired(errorBody, getContext());
                        result = new Gson().fromJson(e, MessageResponse.class);
                        StaticMembers.toastMessageFailed(getContext(), result.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        StaticMembers.toastMessageFailed(getContext(), getActivity().getString(R.string.connection_error));
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });
    }
}
