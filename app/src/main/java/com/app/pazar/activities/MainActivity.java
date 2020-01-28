package com.app.pazar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.app.pazar.R;
import com.app.pazar.baseactivity.BaseActivity;
import com.app.pazar.fragments.AccountFragment;
import com.app.pazar.fragments.DivisionsFragment;
import com.app.pazar.fragments.MessageFragment;
import com.app.pazar.fragments.SlidersFragment;
import com.app.pazar.helpers.StaticMembers;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.pazar.helpers.StaticMembers.CAT;

public class MainActivity extends BaseActivity {

    private static final String SLIDER = "slider";
    private static final String DIV = "div";
    @BindView(R.id.list)
    ImageButton listButton;
    @BindView(R.id.offers)
    Button offers;
    @BindView(R.id.discounts)
    Button discounts;
    @BindView(R.id.account)
    Button account;
    @BindView(R.id.favorite)
    Button favorite;
    @BindView(R.id.gift)
    Button gifts;
    @BindView(R.id.cart)
    Button cart;
    @BindView(R.id.message)
    Button message;
    @BindView(R.id.search)
    TextInputEditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getIntent().getBooleanExtra(CAT, false))
            openFragment(DivisionsFragment.getInstance(), DIV);
        else openFragment(SlidersFragment.getInstance(), SLIDER);
        listButton.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), CategoriesActivity.class)));
        account.setOnClickListener(v -> AccountFragment.getInstance().show(getSupportFragmentManager(), getString(R.string.account)));
        offers.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), OffersActivity.class)));
        discounts.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), DiscountsActivity.class)));
        gifts.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), GiftsActivity.class)));
        message.setOnClickListener(v -> MessageFragment.getInstance().show(getSupportFragmentManager(), getString(R.string.messages)));
        favorite.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), FavoritesActivity.class)));
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(getBaseContext(), SearchProductsActivity.class);
                intent.putExtra(StaticMembers.SEARCH, search.getText().toString());
                startActivity(intent);
                return true;
            }
            return false;
        });
        cart.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), CartActivity.class)));
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(DIV) != null && !getIntent().getBooleanExtra(CAT, false))
            openFragment(SlidersFragment.getInstance(), SLIDER);
        else
                new MaterialStyledDialog.Builder(this)
                .setDescription(R.string.want_to_exit)
                .setPositiveText(R.string.yes)
                .setNegativeText(R.string.no)
                .setIcon(R.mipmap.ic_launcher)
                .setHeaderColor(R.color.colorPrimary)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                    MainActivity.this.finish();

                }).onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    private void openFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
