package com.magdy.abo100.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.magdy.abo100.R;
import com.magdy.abo100.activities.LogInActivity;
import com.magdy.abo100.activities.SplashActivity;
import com.magdy.abo100.helpers.CallbackRetrofit;
import com.magdy.abo100.helpers.PrefManager;
import com.magdy.abo100.helpers.RetrofitModel;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.login_models.EditNameResponse;
import com.magdy.abo100.models.login_models.User;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.magdy.abo100.helpers.StaticMembers.AREA;
import static com.magdy.abo100.helpers.StaticMembers.USER;
import static com.magdy.abo100.helpers.StaticMembers.openLogin;

public class AccountFragment extends DialogFragment {

    public static AccountFragment getInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        Toolbar actionBar = v.findViewById(R.id.toolbar);
        actionBar.setTitle(getString(R.string.account));
        if (actionBar != null) {
            actionBar.setNavigationOnClickListener(v1 -> getDialog().dismiss());
        }
        return v;
    }

    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.gov)
    TextView gov;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.block)
    TextView block;
    @BindView(R.id.street)
    TextView street;
    @BindView(R.id.avenue)
    TextView avenue;
    @BindView(R.id.remarkAddress)
    TextView remarkAddress;
    @BindView(R.id.houseNo)
    TextView houseNo;
    @BindView(R.id.changeLang)
    CardView changeLang;
    @BindView(R.id.signOut)
    CardView signOut;

    private User user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        updateUI();
        changeLang.setOnClickListener(v -> {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            alertDialog.setTitle(R.string.change_language);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1);
            adapter.add("English");
            adapter.add("العربية");
            alertDialog.setAdapter(adapter,
                    (dialog, which) -> {
                        if (adapter.getItem(which) != null)
                            if (adapter.getItem(which).equals("English") && !StaticMembers.getLanguage(getContext()).equals("en")) {
                                StaticMembers.changeLocale(getContext(), "en");
                                StaticMembers.startActivityOverAll(getActivity(), SplashActivity.class);
                            } else if (!StaticMembers.getLanguage(getContext()).equals("ar")) {
                                StaticMembers.changeLocale(getContext(), "ar");
                                StaticMembers.startActivityOverAll(getActivity(), SplashActivity.class);
                            }

                    });

            alertDialog.show();
        });
        signOut.setOnClickListener(v -> {
            PrefManager.getInstance(getContext()).setAPIToken("");
            PrefManager.getInstance(getContext()).setObject(USER, null);
            StaticMembers.startActivityOverAll(getActivity(), LogInActivity.class);
        });
    }

    void openDialog(String key, String nameDisplay, String defaultVal, boolean isReq) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        final EditText input = new EditText(getActivity());
        alertDialog.setTitle(String.format(Locale.getDefault(), getString(R.string.edit_s), nameDisplay));
        input.setHint(key + (isReq ? " *" : ""));
        input.setText(defaultVal);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line
        alertDialog.setPositiveButton(getString(R.string.ok), (dialog, which) -> changeField(key, defaultVal, input.getText().toString(), isReq));
        alertDialog.setNegativeButton(getString(R.string.cancel), null);
        alertDialog.show();
    }

    void openDialog(String key, String nameDisplay, String defaultVal) {
        openDialog(key, nameDisplay, defaultVal, false);
    }

    void updateUI() {
        user = (User) PrefManager.getInstance(getContext()).getObject(USER, User.class);
        if (user == null) {
            openLogin(getContext());
            dismiss();
        } else {
            phone.setText(user.getTelephone());
            email.setText(user.getEmail());
            country.setText(user.getCountry());
            gov.setText(user.getGovernmant());
            block.setText(user.getBlock());
            street.setText(user.getStreet());
            avenue.setText(user.getAvenue());
            name.setText(user.getName());
            area.setText(user.getArea());
            remarkAddress.setText(user.getRemarkaddress());
            houseNo.setText(user.getHouse_no());
            name.setOnClickListener(v -> {
                openDialog(StaticMembers.NAME, getString(R.string.name), user.getName());
            });
            country.setOnClickListener(v -> openDialog(StaticMembers.COUNTRY, getString(R.string.country), user.getCountry(),true));
            gov.setOnClickListener(v -> openDialog(StaticMembers.GOV, getString(R.string.governorate), user.getGovernmant(),true));
            area.setOnClickListener(v -> openDialog(AREA, getString(R.string.area), user.getArea(),true));
            block.setOnClickListener(v -> openDialog(StaticMembers.BLOCK, getString(R.string.block), user.getBlock(),true));
            street.setOnClickListener(v -> openDialog(StaticMembers.STREET, getString(R.string.street), user.getStreet(),true));
            avenue.setOnClickListener(v -> openDialog(StaticMembers.AVENUE, getString(R.string.avenue), user.getAvenue()));
            remarkAddress.setOnClickListener(v -> openDialog(StaticMembers.REMARK_ADDRESS, getString(R.string.remark_address), user.getRemarkaddress()));
            houseNo.setOnClickListener(v -> openDialog(StaticMembers.HOUSE_NO, getString(R.string.house_no), user.getHouse_no()));
        }
    }

    void changeField(String key, String defaultVal, String s, boolean isReq) {
        if (defaultVal != null && defaultVal.equals(s))
            return;
        if (isReq && s.isEmpty()) {
            StaticMembers.toastMessageInfo(getContext(), String.format(Locale.getDefault(), getString(R.string.s_required), key));
            return;
        }
        progress.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put(key, s);
        Call<EditNameResponse> call = RetrofitModel.getApi(getContext()).editField(params);
        call.enqueue(new CallbackRetrofit<EditNameResponse>(getContext()) {
            @Override
            public void onResponse(@NotNull Call<EditNameResponse> call, @NotNull Response<EditNameResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    EditNameResponse result = response.body();
                    if (result != null) {
                        if (result.isStatus()) {
                            PrefManager.getInstance(getContext()).setAPIToken(result.getData().getToken());
                            PrefManager.getInstance(getContext()).setObject(StaticMembers.USER, result.getData().getUser());
                            updateUI();
                        }
                        StaticMembers.toastMessageSuccess(getContext(), result.getMessage());
                    }
                } else {
                    StaticMembers.checkLoginRequired(response.errorBody(), getContext());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EditNameResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });
    }
}
