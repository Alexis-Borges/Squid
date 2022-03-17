package com.example.squid1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.squid.fragments.CartFragment
import com.example.squid.fragments.FavoritesFragment
import com.example.squid.fragments.HomeFragment
import com.example.squid1.fragments.InscriptionFragment
import com.example.squid1.fragments.SearchFragment
import com.example.squid1.fragments.UserFragment
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Product
import com.example.squid1.ProductAdapter
import com.example.squid1.ShoppingCart
import com.example.squid1.ShoppingCartActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var apiService: APIService
    private lateinit var productAdapter: ProductAdapter

    private var products = listOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        apiService = APIConfig.getRetrofitClient(this).create(APIService::class.java)


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        swipeRefreshLayout.isRefreshing = true

//        val layoutManager = StaggeredGridLayoutManager(this, Lin)

        products_recyclerview.layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )


        cart_size.text = ShoppingCart.getShoppingCartSize().toString()

        getProducts()


        showCart.setOnClickListener {

            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

    }


    private fun getProducts() {

        apiService.getProducts().enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.isEnabled = false

                products = response.body()!!

                productAdapter = ProductAdapter(this@MainActivity, products)

                products_recyclerview.adapter = productAdapter

                productAdapter.notifyDataSetChanged()

            }

        })

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






