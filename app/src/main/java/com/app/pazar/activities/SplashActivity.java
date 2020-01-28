package com.app.pazar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.pazar.R;
import com.app.pazar.baseactivity.BaseActivity;
import com.app.pazar.helpers.StaticMembers;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String s = StaticMembers.getLanguage(getBaseContext());
        if (s != null && !s.isEmpty()) {
            StaticMembers.changeLocale(getBaseContext(), s);
        }
        new Handler().postDelayed(() -> {
            Intent intent;
           /* if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
                intent = new Intent(getBaseContext(), LogInActivity.class);
            } else */
            {
                intent = new Intent(getBaseContext(), MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1000);
    }
}