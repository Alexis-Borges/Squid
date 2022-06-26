package com.example.squid1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.squid.R
import com.example.squid1.Cart.ShoppingCartFragment
import com.example.squid1.Fav.FavFragment
import com.example.squid1.Search.SearchFragment
import com.example.squid1.Profile.ContactActivity
import com.example.squid1.fragments.HomeFragment
import com.example.squid1.Profile.ProfileFragment
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
        val favFragment = FavFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()
        val homeFragment = HomeFragment()
        val contactActivity = ContactActivity()

       makeCurrentFragment(homeFragment) //Premier écran affiché après la connexion

        bottom_navigation.setOnItemSelectedListener { //Permets la navigation entre les pages grace à la barre de navigation
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_favorites -> makeCurrentFragment(favFragment)
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_user -> makeCurrentFragment(profileFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
            }
            true
        }
        bottom_navigation.setOnItemReselectedListener { //Permets la régénération des pages grace à la barre de navigation
            when (it.itemId) {
                R.id.ic_home -> refreshCurrentFragment(homeFragment)
                R.id.ic_favorites -> refreshCurrentFragment(favFragment)
                R.id.ic_search -> refreshCurrentFragment(searchFragment)
                R.id.ic_cart -> refreshCurrentFragment(cartFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) = //adapte la taille de page pour ne pas dépasser sur la barre de navigation ou sur le harder
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    fun refreshCurrentFragment(fragment: Fragment) { //la Fonction qui permets la régénération des pages grace à la barre de navigation
        var currentFragment = supportFragmentManager.findFragmentById(fragment.id)!!
        supportFragmentManager.beginTransaction().detach(currentFragment).commit()
        supportFragmentManager.beginTransaction().attach(currentFragment).commit()
    }
}





