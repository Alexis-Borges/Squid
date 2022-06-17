package com.example.squid1.Login

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.squid.R
import android.content.Intent
import android.view.View
import com.example.squid1.Login.RegisterActivity
import okhttp3.ResponseBody
import com.example.squid1.Login.RetrofitClient
import android.widget.Toast
import com.example.squid1.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        etUsername = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etPassword)
        findViewById<View>(R.id.btnLogin).setOnClickListener { loginUser() }
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
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    var s = ""
                    try {
                        s = response.body()!!.string()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (s == "") {
                        Toast.makeText(this@LoginActivity, "User logged in!", Toast.LENGTH_LONG).show()
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            ).putExtra("username", userName)
                        )
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Welcome ! Happy Squidding",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            ).putExtra("username", userName)
                        )
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}