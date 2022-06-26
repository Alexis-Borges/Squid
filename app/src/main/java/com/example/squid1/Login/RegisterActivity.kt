package com.example.squid1.Login


import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.squid.R
import android.content.Intent
import android.view.View
import okhttp3.ResponseBody
import android.widget.Toast
import com.example.squid1.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class RegisterActivity : AppCompatActivity() { //Activiter d'inscription
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)
        etUsername = findViewById(R.id.etRUserName)
        etPassword = findViewById(R.id.etRPassword)

        findViewById<View>(R.id.btnRegister).setOnClickListener {

            if (Utilities.isValidMail(etUsername.text.toString())) { //Verification du champs qui doit etre composer d'un @ et d'un .com
                registerUser()
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Email Field Incorrect   🚫",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        findViewById<View>(R.id.tvLoginLink).setOnClickListener { //Permet le changement de page si l'utilisateur a un compte et souhaite se connecter
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }
    }

    private fun registerUser() {
        val userName = etUsername!!.text.toString().trim { it <= ' ' } //Vérification que le champ ne soit pas vide
        val password = etPassword!!.text.toString().trim { it <= ' ' } //Vérification que le champ ne soit pas vide
        if (userName.isEmpty()) {
            etUsername!!.error = "Email is required"
            etUsername!!.requestFocus()
            return
        } else if (password.isEmpty()) {
            etPassword!!.error = "Password is required"
            etPassword!!.requestFocus()
            return
        }
        RetrofitClient
            .getInstance()
            .api
            .createUser(User(userName, password))?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.code() == HttpURLConnection.HTTP_OK) { //si le champ email est unique et que le format correspond le compte est créé et le renvoie sur la page de connexion

                        Toast.makeText(
                            this@RegisterActivity,
                            "Successfully registered. Please login",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    } else  {
                        Toast.makeText(
                            this@RegisterActivity,
                            "User already exists!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_LONG).show()
                }


            })
    }
}

