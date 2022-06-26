package com.example.squid1.Profile


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
import com.example.squid1.fragments.HomeFragment
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_info_user.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK


class infoUserActivity : AppCompatActivity() {
        //Affichage des informations de l'utilisateur
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_user)

        val token = applicationContext?.let { AuthManagement.getToken(this) }
        val jwt = token?.let { JWT(it) }

        val userEmail = jwt?.getClaim("email")?.asString()
        val userAdmin = jwt?.getClaim("isAdmin")?.asString()
        val userName = jwt?.getClaim("firstName")?.asString()
        val userLastName = jwt?.getClaim("lastName")?.asString()


        email.text = userEmail
        admin.text = userAdmin
        firstName.text = userName
        lastName.text = userLastName
    }
}