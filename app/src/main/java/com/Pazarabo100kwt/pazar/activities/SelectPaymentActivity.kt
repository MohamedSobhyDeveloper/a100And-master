package com.Pazarabo100kwt.pazar.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.Pazarabo100kwt.pazar.R
import com.Pazarabo100kwt.pazar.baseactivity.BaseActivity
import com.Pazarabo100kwt.pazar.helpers.CallbackRetrofit
import com.Pazarabo100kwt.pazar.helpers.RetrofitModel
import com.Pazarabo100kwt.pazar.helpers.StaticMembers
import com.Pazarabo100kwt.pazar.models.cart.Data
import com.Pazarabo100kwt.pazar.models.order_models.StoreOrderResponse
import kotlinx.android.synthetic.main.activity_select_payment.*
import kotlinx.android.synthetic.main.progress_layout.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response


class SelectPaymentActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_payment)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { v -> onBackPressed() }
        done.setOnClickListener { v -> orderNow() }

    }

    private fun orderNow() {
        var noSelected = true
        val cartData = intent.getSerializableExtra(StaticMembers.CART) as Data
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        for (cart in cartData.cart) {
            if (!cart.isNotChecked) {
                noSelected = false
                builder.addFormDataPart("product_id[]", "" + cart.productId)
            }
        }
        if (noSelected) {
            StaticMembers.toastMessageInfo(this,getString(R.string.select_at_least_one))
            onBackPressed()
            return
        }
        progress.visibility = VISIBLE
        val call = RetrofitModel.getApi(this).storeOrder(builder.build())
        call.enqueue(object : CallbackRetrofit<StoreOrderResponse>(this) {
            override fun onResponse(call: Call<StoreOrderResponse>, response: Response<StoreOrderResponse>) {
                progress.visibility = View.GONE
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    StaticMembers.toastMessageSuccess(baseContext, result.message)
                    setResult(Activity.RESULT_OK)
                    startActivity(Intent(this@SelectPaymentActivity, ConfirmBillActivity::class.java))
                    finish()
                } else {
                    StaticMembers.checkLoginRequired(response.errorBody(), this@SelectPaymentActivity)
                }
            }

            override fun onFailure(call: Call<StoreOrderResponse>, t: Throwable) {
                super.onFailure(call, t)
                progress.visibility = View.GONE
            }
        })

    }
}
