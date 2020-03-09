package com.Pazarabo100kwt.pazar.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.Pazarabo100kwt.pazar.activities.ConfirmBillActivity;
import com.Pazarabo100kwt.pazar.activities.MainActivity;
import com.Pazarabo100kwt.pazar.models.order_models.StoreOrderResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.activities.LogInActivity;
import com.Pazarabo100kwt.pazar.activities.SelectPaymentActivity;
import com.Pazarabo100kwt.pazar.models.login_models.ErrorLoginResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class StaticMembers {

    public static final String domain = "http://aboo100.com/shop/public/api/";
    public static final String CAT = "categories";
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ID = "category_id";
    public static final String USERS = "users";
    public static final String USER = "user";
    public static final String FAV = "fav";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_FORMAT_VIEW = "yyyy-M-dd hh:mm";
    public static final String SIGN_UP = "sign_up";
    public static final String PIN_CODE = "pincode";
    public static final String PIN_CODE_TOKEN = "pincodetoken";
    public static final String PRODUCT = "product";
    public static final String NAME = "name";
    public static final String GOV = "governmant";
    public static final String AREA = "area";
    public static final String BLOCK = "block";
    public static final String STREET = "street";
    public static final String AVENUE = "avenue";
    public static final String REMARK_ADDRESS = "remarkaddress";
    public static final String HOUSE_NO = "house_no";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final int LOCATION_CODE = 5544;
    public static final String COUNTRY = "country";
    private static final String LANGUAGE = "language";
    public static final String SEARCH = "search";
    public static final String SUB_CATEGORY = "subcategory";
    public static final String ID = "id";
    public static final String WISHLIST = "wishlist";
    public static final String WISHLIST_ACTION = "wishlist/action/{id}";
    public static final String SORT = "sort";
    public static final String AMOUNT = "amount";
    public static final String CART = "cart";
    public static final String EDIT_CART = "cart/store";
    public static final String QUANTITY = "quantity";
    public static final String PRODUCT_ID = "product_id";
    public static final String DELETE_CART = "cart/delete/{id}";
    public static final String VIDEO = "video";
    public static final String IMAGE = "image";
    public static final String STOP = "stop";
    public static final String ACTION = "action";
    public static final String SLIDER = "slider";
    public static final String EDIT_NAME = "profile/edit";
    public static final String CONTACT = "contact";
    public static final String MESSAGE = "message";
    public static final String GIFT = "gift";
    public static final String ORDER_STORE = "orders/store";
    public static final String CODE = "code";
    public static final String MAX_AMOUNT = "max";
    public static final String UNIT_SIZE = "unitsize";
    public static final String COLOR = "color";
    public static final String SUB_CATEGORY_ID = "subcategory_id";
    public static final String OFFERS = "offer";
    public static final String DISCOUNT = "discount";
    public static final String GIFT_GET = "gifts/all";
    public static final String EMAIL = "email";
    public static final String telephone = "telephone";
    public static final String PASSWORD = "password";
    public static final String LAT = "latitude";
    public static final String LONG = "longitude";
    public static final String ADDRESS = "address";
    public static final String TOKEN = "token";
    public static final String PAGE = "page";
    public static final String register = "register";
    public static final String getPinCode = "user/getPinCode";
    public static final String login = "login";
    public static final String verifyPinCode = "user/testpincode";
    public static final String reset = "user/password";


    ////////////////// change Dots////////////////////
    public static void changeDots(int currentPage, int count, LinearLayout dotsLayout, Context context) {
        ImageView[] dots = new ImageView[count];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(context);
            dots[i].setImageResource(R.drawable.bullet_unselected);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > currentPage)
            dots[currentPage].setImageResource(R.drawable.bullet_selected);
    }


    /////////////////Dates converter/////////////////////
    public static String changeDateFromIsoToView(String dateFrom) {
        SimpleDateFormat sdf = new SimpleDateFormat(StaticMembers.ISO_DATE_FORMAT, Locale.US);
        SimpleDateFormat sdfTo = new SimpleDateFormat(StaticMembers.DATE_FORMAT_VIEW, Locale.getDefault());
        try {
            return sdfTo.format(sdf.parse(dateFrom));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFrom;
    }

    public static <T extends Activity> void startActivityOverAll(T activity, Class<?> destinationActivity) {
        Intent intent = new Intent(activity, destinationActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public static <T extends Activity> void startActivityOverAll(Intent intent, T activity) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public static <T extends View> void hideKeyboard(T view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static <A extends Activity> void hideKeyboard(A activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    //////////////////////Password validation/////////////////////
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean CheckValidationPassword(TextInputEditText editText, final TextInputLayout textInputLayout, final String errorMessage, final String errorMessage2) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    textInputLayout.setError(errorMessage);
                    textInputLayout.setErrorEnabled(true);
                } else {
                    if (!isValidPassword(s.toString())) {

                        textInputLayout.setError(errorMessage2);
                        textInputLayout.setErrorEnabled(true);
                    } else {
                        textInputLayout.setErrorEnabled(false);
                    }
                }

            }
        });
        if (TextUtils.isEmpty(editText.getText())) {
            textInputLayout.setError(errorMessage);
            textInputLayout.setErrorEnabled(true);
            return false;
        } else {
            if (!isValidPassword(editText.getText().toString())) {
                textInputLayout.setError(errorMessage2);
                textInputLayout.setErrorEnabled(true);
                return false;
            } else {
                textInputLayout.setErrorEnabled(false);
                return true;
            }
        }
    }
    //////////////////////Visiblity with Animation/////////////////////

    public static <V extends View> void makeVisible(V layout) {
        layout.setVisibility(View.VISIBLE);
        layout.setAlpha(0.0f);
        layout.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(null);
    }

    public static <V extends View> void makeGone(V layout) {
        layout.setVisibility(View.GONE);
    }
    //////////////////////Toasts/////////////////////


    public static void toastMessageSuccess(Context context, String messaage) {
        Toasty.success(context, messaage, Toast.LENGTH_SHORT, true).show();

    }

    public static void toastMessageFailed(Context context, String messaage) {
        Toasty.error(context, messaage, Toast.LENGTH_SHORT, true).show();

    }

    public static void toastMessageInfo(Context context, String messaage) {
        Toasty.info(context, messaage, Toast.LENGTH_SHORT, true).show();
    }




    public static boolean CheckTextInputEditText(TextInputEditText editText, final TextInputLayout textInputLayout, final String errorMessage) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    textInputLayout.setError(errorMessage);
                    textInputLayout.setErrorEnabled(true);
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        if (TextUtils.isEmpty(editText.getText())) {
            textInputLayout.setError(errorMessage);
            textInputLayout.setErrorEnabled(true);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

//    public static boolean isValidPassword(String password) {
//        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(password);
//        return matcher.matches();
//    }

    public static String getLanguage(Context context) {
        return PrefManager.getInstance(context).getStringData(LANGUAGE);
    }

    public static void setLanguage(Context context, String langStr) {
        PrefManager.getInstance(context).setStringData(LANGUAGE, langStr);
    }

    public static void changeLocale(Context context, String langStr) {
        setLanguage(context, langStr);
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(langStr.toLowerCase()));
        res.updateConfiguration(conf, dm);
        Locale locale = context.getResources().getConfiguration().locale;
        Locale.setDefault(locale);
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd hh:mma", Locale.getDefault()).format(calendar.getTime());

    }

    public static void openLogin(Context activity) {
        Intent intent = new Intent(activity, LogInActivity.class);
        intent.putExtra(StaticMembers.ACTION, true);
        activity.startActivity(intent);
    }

    public static void checkLoginRequired(ResponseBody errorBody, Context context) {
        try {
            ErrorLoginResponse errorLoginResponse = null;
            if (errorBody != null) {
                errorLoginResponse = new GsonBuilder().create().fromJson(errorBody.string(), ErrorLoginResponse.class);
                if (errorLoginResponse != null && errorLoginResponse.getError() != null) {
                    if (!errorLoginResponse.getError().isEmpty() && errorLoginResponse.getError().toLowerCase().contains("token")) {
                        PrefManager.getInstance(context).removeToken();
                        openLogin(context);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(context, R.string.connection_error, Toast.LENGTH_SHORT, true).show();

        }
    }


    @SuppressLint("SetTextI18n")
    public static void opendetailsdialog(Context context,StoreOrderResponse storeOrderResponse) {
        Dialog dialogview = new Dialog(context);
        dialogview.setContentView(R.layout.create_details_popup);
        dialogview.setCanceledOnTouchOutside(false);
        dialogview.setCancelable(false);
        dialogview.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);

        CardView home = dialogview.findViewById(R.id.home);
        TextView delivery_charge = dialogview.findViewById(R.id.delivery_charge);
        TextView total = dialogview.findViewById(R.id.total);
        TextView payment_method = dialogview.findViewById(R.id.payment_method);
        TextView address = dialogview.findViewById(R.id.address);
        TextView order_number = dialogview.findViewById(R.id.order_number);
        LinearLayout llayout_item = dialogview.findViewById(R.id.llayout_item);
        TextView close = dialogview.findViewById(R.id.close);


        total.setText(storeOrderResponse.getData().getResult().getTotalAmount()+" "+context.getString(R.string.kd));
        payment_method.setText(storeOrderResponse.getData().getResult().getPaymentWay()+"");
        order_number.setText(context.getString(R.string.order_)+" "+"#"+" "+storeOrderResponse.getData().getResult().getId()+"");
        delivery_charge.setText(storeOrderResponse.getData().getResult().getDeliverycharge()+" "+context.getString(R.string.kd));
        address.setText(storeOrderResponse.getData().getResult().getAddress()+"");


        home.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });

        close.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });


        for (int i=0;i<storeOrderResponse.getData().getResult().getItem().size();i++){

            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View rowView;
            if (inflater != null) {

                rowView = inflater.inflate(R.layout.item_product_inflate, null);
                TextView name = rowView.findViewById(R.id.name);
                TextView productId = rowView.findViewById(R.id.productId);
                TextView amount = rowView.findViewById(R.id.amount);
                TextView price = rowView.findViewById(R.id.price);
                TextView unit = rowView.findViewById(R.id.unit);
                TextView color = rowView.findViewById(R.id.color);

                if (storeOrderResponse.getData().getResult().getItem().get(i).getUnit()!=null&&!storeOrderResponse.getData().getResult().getItem().get(i).getUnit().equals("0")&&!storeOrderResponse.getData().getResult().getItem().get(i).getUnit().equals("")){
                    unit.setVisibility(View.VISIBLE);
                    unit.setText(context.getString(R.string.shapesize)+" : "+storeOrderResponse.getData().getResult().getItem().get(i).getUnit()+"");

                }else{
                    unit.setVisibility(View.GONE);
                }

           if (!storeOrderResponse.getData().getResult().getItem().get(i).getColor_name().equals("")){
               color.setVisibility(View.VISIBLE);
               color.setText(context.getString(R.string.color)+" : "+storeOrderResponse.getData().getResult().getItem().get(i).getColor_name()+"");

           }else {
               color.setVisibility(View.GONE);
           }

                name.setText(storeOrderResponse.getData().getResult().getItem().get(i).getProduct_name()+"");
                productId.setText(storeOrderResponse.getData().getResult().getItem().get(i).getProduct_no()+"-"+storeOrderResponse.getData().getResult().getItem().get(i).getSubcode());
                amount.setText(storeOrderResponse.getData().getResult().getItem().get(i).getQuantity());

//                double pricevlaue= Double.parseDouble(storeOrderResponse.getData().getResult().getItem().get(i).getPrice());
//                double quantity= Integer.parseInt(storeOrderResponse.getData().getResult().getItem().get(i).getQuantity());
//
//                double totalvalue=pricevlaue*quantity;

                price.setText(storeOrderResponse.getData().getResult().getItem().get(i).getPrice()+" "+context.getString(R.string.kd));



                llayout_item.addView(rowView);
            }


        }





        dialogview.show();


        }



    }



