package com.example.squid1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.squid.fragments.CartFragment
import com.example.squid.fragments.FavoritesFragment
import com.example.squid1.fragments.InscriptionFragment
import com.example.squid1.fragments.SearchFragment
import com.example.squid1.fragments.UserFragment
import android.content.Intent
import com.example.squid.R
import com.example.squid1.fragments.BlankFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        cart_size.text = ShoppingCart.getShoppingCartSize().toString()


        showCart.setOnClickListener {

            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

        val favoritesFragment = FavoritesFragment()
        val cartFragment = CartFragment()
        val searchFragment = SearchFragment()
        val userFragment = UserFragment()
        val inscriptionFragment = InscriptionFragment()
        val blankFragment = BlankFragment()

       makeCurrentFragment(blankFragment)


        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(blankFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
                R.id.ic_favorites -> makeCurrentFragment(favoritesFragment)
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_user -> makeCurrentFragment(userFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}





