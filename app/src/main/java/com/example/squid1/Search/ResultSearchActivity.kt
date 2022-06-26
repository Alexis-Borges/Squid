package com.example.squid1.Search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.squid.databinding.ActivityResultSearchBinding
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Product
import com.example.squid1.ProductAdapter
import retrofit2.Call
import retrofit2.Response


/**
 * Barre de navigation !
 */
class ResultSearchActivity : AppCompatActivity() {

    // This property is only valid between onCreateView and onDestroyView.
    var product = listOf<Product>()
    private lateinit var binding: ActivityResultSearchBinding
    private lateinit var apiService: APIService
    private lateinit var productAdapter: ProductAdapter


    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.extras?.containsKey("CATEGORY") == true) {

            val category = intent.extras!!.getInt("CATEGORY")
            getlistProductSortByCategory(category)

            Log.d("listProductSortByCategory", category.toString())
            Log.d("listValue", product.toString())

            binding.recyclerViewSearchResult.apply {
                layoutManager =
                    GridLayoutManager(applicationContext, 2)
                adapter = ProductAdapter(applicationContext,this@ResultSearchActivity, product)
            }

        }
    }

    private fun getlistProductSortByCategory(categoryId: Int) {


        apiService = applicationContext?.let {
            APIConfig.getRetrofitClient(it).create(APIService::class.java)
        }!!


        apiService.getProductsByCategory(categoryId).enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                Log.d("Data response",response.body().toString() )

                product = response.body()!!

                productAdapter = ProductAdapter(applicationContext, this@ResultSearchActivity, product)

                productAdapter.notifyDataSetChanged()

            }

        })
    }

}