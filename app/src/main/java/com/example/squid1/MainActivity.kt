package com.example.squid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.squid.fragments.*
import com.example.squid1.fragments.InscriptionFragment
import com.example.squid1.fragments.SearchFragment
import com.example.squid1.fragments.UserFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*


class MainActivity : AppCompatActivity()  {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val homeFragment = HomeFragment()
        val favoritesFragment = FavoritesFragment()
        val cartFragment = CartFragment()
        val searchFragment = SearchFragment()
        val userFragment = UserFragment()
        val inscriptionFragment = InscriptionFragment()


       makeCurrentFragment(homeFragment)


        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
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






