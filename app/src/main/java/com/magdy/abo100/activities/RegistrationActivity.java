package com.magdy.abo100.activities;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.magdy.abo100.R;
import com.magdy.abo100.baseactivity.BaseActivity;
import com.magdy.abo100.helpers.CallbackRetrofit;
import com.magdy.abo100.helpers.PrefManager;
import com.magdy.abo100.helpers.RetrofitModel;
import com.magdy.abo100.helpers.StaticMembers;
import com.magdy.abo100.models.area_models.AreaResponse;
import com.magdy.abo100.models.area_models.DataItem;
import com.magdy.abo100.models.registration_models.ErrorRegistrationResponse;
import com.magdy.abo100.models.registration_models.RegistrationResponse;
import com.magdy.abo100.models.registration_models.RegistrationSendModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.email)
    TextInputEditText emailText;
    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.name)
    TextInputEditText nameText;
    @BindView(R.id.nameLayout)
    TextInputLayout nameLayout;
    @BindView(R.id.mobile)
    TextInputEditText phone;
    @BindView(R.id.phoneLayout)
    TextInputLayout phoneLayout;
    @BindView(R.id.countryLayout)
    TextInputLayout countryLayout;
    @BindView(R.id.country)
    TextInputEditText country;
    @BindView(R.id.govLayout)
    TextInputLayout govLayout;
    @BindView(R.id.gov)
    TextInputEditText gov;
    @BindView(R.id.areaLayout)
    TextInputLayout areaLayout;
    @BindView(R.id.area)
    TextInputEditText area;
    @BindView(R.id.blockLayout)
    TextInputLayout blockLayout;
    @BindView(R.id.block)
    TextInputEditText block;
    @BindView(R.id.streetLayout)
    TextInputLayout streetLayout;
    @BindView(R.id.street)
    TextInputEditText street;
    @BindView(R.id.avenueLayout)
    TextInputLayout avenueLayout;
    @BindView(R.id.avenue)
    TextInputEditText avenue;
    @BindView(R.id.remarkAddressLayout)
    TextInputLayout remarkAddressLayout;
    @BindView(R.id.remarkAddress)
    TextInputEditText remarkAddress;
    @BindView(R.id.houseNoLayout)
    TextInputLayout houseNoLayout;
    @BindView(R.id.houseNo)
    TextInputEditText houseNo;
    @BindView(R.id.password)
    TextInputEditText passwordText;
    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;
    @BindView(R.id.confirmPassword)
    TextInputEditText confirmPassword;
    @BindView(R.id.confirmPasswordLayout)
    TextInputLayout confirmPasswordLayout;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.skip)
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        findViewById(R.id.register).setOnClickListener(v -> register());
        confirmPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE)
                register();
            return false;
        });
        skip.setOnClickListener(v -> StaticMembers.startActivityOverAll(this, MainActivity.class));
        List<String> areas = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areas);

    }

    void register() {
        if (StaticMembers.CheckTextInputEditText(emailText, emailLayout, getString(R.string.email_empty)) &&
                StaticMembers.CheckTextInputEditText(nameText, nameLayout, getString(R.string.full_name_empty)) &&
                StaticMembers.CheckTextInputEditText(phone, phoneLayout, getString(R.string.phone_empty)) &&
                StaticMembers.CheckTextInputEditText(phone, phoneLayout, getString(R.string.phone_empty)) &&
                StaticMembers.CheckTextInputEditText(country, countryLayout, getString(R.string.countryـreq)) &&
                StaticMembers.CheckTextInputEditText(gov, govLayout, getString(R.string.governorateـreq)) &&
                StaticMembers.CheckTextInputEditText(area, areaLayout, getString(R.string.area_req)) &&
                StaticMembers.CheckTextInputEditText(block, blockLayout, getString(R.string.block_req)) &&
                StaticMembers.CheckTextInputEditText(street, streetLayout, getString(R.string.street_req)) &&
                //StaticMembers.CheckValidationPassword(passwordText, passwordLayout, getString(R.string.password_empty), getString(R.string.password_invalid_error)) &&
                StaticMembers.CheckTextInputEditText(confirmPassword, confirmPasswordLayout, getString(R.string.confirm_password_empty))) {
            final String name = nameText.getText().toString();
            final String email = emailText.getText().toString();
            final String password = passwordText.getText().toString();
            final String confirm = confirmPassword.getText().toString();
            if (!password.equals(confirm)) {
                StaticMembers.toastMessageInfo(getBaseContext(), getString(R.string.password_doesnt_match));
                return;
            }
            progress.setVisibility(View.VISIBLE);
            final RegistrationSendModel model =
                    new RegistrationSendModel(name, phone.getText().toString(), email, password);
            model.setCountry(country.getText().toString());
            model.setGovernmant(gov.getText().toString());
            model.setArea(area.getText().toString());
            model.setAvenue(avenue.getText().toString());
            model.setBlock(block.getText().toString());
            model.setStreet(street.getText().toString());
            model.setRemarkaddress(remarkAddress.getText().toString());
            model.setHouse_no(houseNo.getText().toString());
            Call<RegistrationResponse> call = RetrofitModel.getApi(this).register(model);
            call.enqueue(new CallbackRetrofit<RegistrationResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                    progress.setVisibility(View.GONE);
                    RegistrationResponse result = response.body();
                    if (response.isSuccessful() && result != null) {
                        StaticMembers.toastMessageSuccess(getBaseContext(), result.getMessage());
                        PrefManager.getInstance(getBaseContext()).setAPIToken(result.getData().getToken());
                        PrefManager.getInstance(getBaseContext()).setObject(StaticMembers.USER, result.getData().getUser());
                        StaticMembers.startActivityOverAll(RegistrationActivity.this, MainActivity.class);
                    } else {
                        try {
                            ErrorRegistrationResponse errorLoginResponse = null;
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorRegistrationResponse.class);
                                if (errorLoginResponse != null && errorLoginResponse.getMessage() != null) {
                                    StaticMembers.toastMessageFailed(getBaseContext(), errorLoginResponse.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            StaticMembers.toastMessageFailed(getBaseContext(), getString(R.string.connection_error));
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    progress.setVisibility(View.GONE);
                }
            });

        }
    }
}