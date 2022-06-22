package com.example.squid1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.squid.R
import com.example.squid1.Cart.ShoppingCartFragment
import com.example.squid1.Login.LoginActivity
import com.example.squid1.Search.SearchFragment
import com.example.squid1.fragments.BlankFragment
import com.example.squid1.fragments.BookmarkFragment
import com.example.squid1.fragments.ProfileFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cart.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)


        val cartFragment = ShoppingCartFragment()
        val bookmarkFragment = BookmarkFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()
        val blankFragment = BlankFragment()

       makeCurrentFragment(blankFragment)

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(blankFragment)
                R.id.ic_favorites -> makeCurrentFragment(bookmarkFragment)
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_user -> makeCurrentFragment(profileFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
            }
            true
        }
        bottom_navigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ic_home -> refreshCurrentFragment(blankFragment)
                R.id.ic_favorites -> refreshCurrentFragment(bookmarkFragment)
                R.id.ic_search -> refreshCurrentFragment(searchFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    fun refreshCurrentFragment(fragment: Fragment) {
        var currentFragment = supportFragmentManager.findFragmentById(fragment.id)!!
        supportFragmentManager.beginTransaction().detach(currentFragment).commit()
        supportFragmentManager.beginTransaction().attach(currentFragment).commit()
    }
}





