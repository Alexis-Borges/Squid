package com.example.squid1.Login

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.squid.R
import android.content.Intent
import android.view.View
import okhttp3.ResponseBody
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.example.squid1.MainActivity
import com.example.squid1.Utilities
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.net.HttpURLConnection.HTTP_OK


class LoginActivity : AppCompatActivity() {
     lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        etUsername = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etPassword)

        findViewById<View>(R.id.btnLogin).setOnClickListener {

            if (Utilities.isValidMail(etUsername.text.toString())) {
                loginUser()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Email Field Incorrect   🚫",
                    Toast.LENGTH_LONG
                ).show()
            }

        }


        findViewById<View>(R.id.tvRegisterLink).setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
    }

    private fun loginUser() {
        val userName = etUsername!!.text.toString().trim { it <= ' ' }
        val password = etPassword!!.text.toString().trim { it <= ' ' }
        if (userName.isEmpty()) {
            etUsername!!.error = "Email is required"
            etUsername!!.requestFocus()
            return
        } else if (password.isEmpty()) {
            etPassword!!.error = "Password is required"
            etPassword!!.requestFocus()
            return
        }
        val call = RetrofitClient
            .getInstance()
            .api
            .checkUser(User(userName, password))
        if (call != null) {
            call.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {

                    if (response.code() == HTTP_OK) {
                        response.body()
                            ?.let { AuthManagement.saveToken(it.string(), this@LoginActivity) }

                        Toast.makeText(
                            this@LoginActivity,
                            "User logged in ! Welcome, Happy Squidding",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                        )
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid credentials try again!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}