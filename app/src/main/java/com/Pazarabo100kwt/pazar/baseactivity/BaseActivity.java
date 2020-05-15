package com.Pazarabo100kwt.pazar.baseactivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.Pazarabo100kwt.pazar.helpers.StaticMembers;

import java.util.Locale;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("CurrentScreen", this.getClass().getSimpleName());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setuplanguages();

    }


    private void setuplanguages() {
        String s = StaticMembers.getLanguage(getBaseContext());
        if (s != null && !s.isEmpty()) {
            StaticMembers.changeLocale(getBaseContext(), s);
        }else {
            StaticMembers.changeLocale(getBaseContext(), "ar");
        }
    }
//    @Override
//    public void onBackPressed() {
//        new MaterialStyledDialog.Builder(this)
//                .setDescription(R.string.want_to_exit)
//                .setPositiveText(R.string.yes)
//                .setNegativeText(R.string.no)
//                .setIcon(R.mipmap.ic_launcher)
//                .setHeaderColor(R.color.colorPrimary)
//                .withDialogAnimation(true)
//                .withIconAnimation(true)
//                .onPositive((dialog, which) -> {
//                    dialog.dismiss();
//                    android.os.Process.killProcess(android.os.Process.myPid());
//                    System.exit(1);
//                    BaseActivity.this.finish();
//
//                }).onNegative((dialog, which) -> dialog.dismiss())
//                .show();
//    }

}
