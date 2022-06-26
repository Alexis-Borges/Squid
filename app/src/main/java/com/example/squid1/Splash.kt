package com.example.squid1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.squid.R
import com.example.squid1.Login.LoginActivity
import kotlinx.android.synthetic.main.splash.*

class Splash : AppCompatActivity() { //Écran de chargement lors de l'exécution de l'application
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        icon.alpha = 0f
        icon.animate().setDuration(2500).alpha(1f).withEndAction {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}