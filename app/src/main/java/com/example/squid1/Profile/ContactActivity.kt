package com.example.squid1.Profile


import android.annotation.SuppressLint
import android.os.Build

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity

import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.UserContact
import com.example.squid1.Login.AuthManagement
import kotlinx.android.synthetic.main.activity_contact.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactActivity : AppCompatActivity() { //Activité Formulaire de contact
    private lateinit var apiService: APIService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val token = applicationContext?.let { AuthManagement.getToken(this) }
        val jwt = token?.let { JWT(it) }

        val userEmail = jwt?.getClaim("email")?.asString()

        val firstName = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val lastName = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()  //Récupération des informations rentraient dans les champs
        val subject = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
        val message = findViewById<EditText>(R.id.editTextTextMultiLine).text.toString()

        buttonEnv.setOnClickListener {
            apiService =
                APIConfig.getRetrofitClient(applicationContext.applicationContext)
                    .create(APIService::class.java)
            apiService.contactinfo(
                    UserContact(firstName, lastName, email, subject, message)) //envoie à l'api du formulaire avec ces informations
                .enqueue(object :
                    Callback<ResponseBody> {
                    @SuppressLint("RestrictedApi")
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Toast.makeText(applicationContext, "Merci, votre message a bien été envoyer", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Error", t.message.toString())
                    }

                })
            finish()
        }
//        editTextTextEmailAddress.text = userEmail
    }
}