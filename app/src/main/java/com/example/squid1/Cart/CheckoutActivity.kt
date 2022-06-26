package com.example.squid1.Cart


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Login.AuthManagement
import com.example.squid1.MainActivity
import com.example.squid1.fragments.HomeFragment
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.android.synthetic.main.activity_checkout.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

//Activité permettant le bon deroulement du paiement par Stripe
class CheckoutActivity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentSheet: PaymentSheet

    private lateinit var payButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        //Recuperation de la clé Stripe
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

    private fun fetchPaymentIntent() { //fonction permettant de crée l'instance de paiement (savoir si l'utilisateur est connecter et qu'il a bien des objet dans son panier)
        apiService = this.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!
        val token = AuthManagement.getToken(this)
        val jwt = token?.let { JWT(it) }

        var userId = jwt?.getClaim("id")?.asString().toString()

        apiService.pay(userId, jwt.toString()).enqueue(object :
            Callback<JsonObject> {
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
        //fin de transaction appel a l'Api pour savoir si l'utilisateur a bien validé la commande et lui faire un retour si ce n'est pas le cas
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                apiService =
                    this.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!
                val token = AuthManagement.getToken(this)
                val jwt = token?.let { JWT(it) }

                var userId = jwt?.getClaim("id")?.asString().toString()

                apiService.ConfirmedOrder(userId, jwt.toString()).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.code() == HTTP_OK) { //reception d'une reponse 200, l'achat a bien été validé
                            Toast.makeText(applicationContext, "Achat Validé, Votre Panier a été Vidé", Toast.LENGTH_SHORT).show()
                            finish()
                            startActivity(Intent(applicationContext, MainActivity::class.java));


                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Error", t.message.toString())
                    }
                })
            }
            is PaymentSheetResult.Canceled -> {//si l'utilisateur est revenu en arrière et na pas valider ca commande
                Toast.makeText(applicationContext, "Achat annuler", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {//si l'utilisateur na pas asser de fond pour valider ca commande
                Toast.makeText(applicationContext, "Echec", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()

            }
        }

        return super.onOptionsItemSelected(item)
    }
}