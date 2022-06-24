package com.example.squid1.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Product
import com.example.squid1.ProductAdapter
import kotlinx.android.synthetic.main.fragment_blank.*
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var apiService: APIService
    private lateinit var productAdapter: ProductAdapter

    private var products = listOf<Product>()

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        getProducts()
    }


    private fun getProducts() {

        products_recyclerview.layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        apiService = activity?.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!


        apiService.getProducts().enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                products = response.body()!!

                productAdapter = activity?.let { ProductAdapter(it, it, products ) }!!

                products_recyclerview.adapter = productAdapter

                productAdapter.notifyDataSetChanged()

            }

        })

    }
}