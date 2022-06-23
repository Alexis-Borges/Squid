package com.example.squid1.Cart

import android.content.ContentValues.TAG
import android.util.Log
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Cartitem
import com.example.squid1.Login.AuthManagement
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.android.synthetic.main.activity_checkout.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.HTTP
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK

class CheckoutActivity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentSheet: PaymentSheet

    private lateinit var payButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51LDUVDIvLK6IY2HZ02Al4kK8CiWBwcfR3tDgl2aGbNJtLW4klNiRcWdWBHOz5zsUFAKgi41mbeu2JpbqZHV6kwQZ00vPP4XerN"
        )

        // Hook up the pay button
        payButton = findViewById(R.id.pay_button)
        payButton.setOnClickListener(::onPayClicked)
        payButton.isEnabled = false

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

        fetchPaymentIntent()
    }

    private fun fetchPaymentIntent() {
        apiService = this.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!
        val token = AuthManagement.getToken(this)
        val jwt = token?.let { JWT (it) }

        var userId = jwt?.getClaim("id")?.asString().toString()

        apiService.pay(userId, jwt.toString()).enqueue(object :
            Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == HTTP_OK) {
                    pay_button.isClickable = true
                    pay_button.isEnabled = true
                    paymentIntentClientSecret = response.body()!!.get("clientSecret").asString
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun onPayClicked(view: View) {
        val configuration = PaymentSheet.Configuration("Example, Inc.")

        // Present Payment Sheet
        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {

            }
            is PaymentSheetResult.Canceled -> {

            }
            is PaymentSheetResult.Failed -> {

            }
        }
    }
}