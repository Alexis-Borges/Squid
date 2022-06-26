package com.example.squid1.Fav

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.squid.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.auth0.android.jwt.JWT
import com.example.squid.databinding.FragmentCartBinding
import com.example.squid1.Api.*
import com.example.squid1.Cart.CheckoutActivity
import com.example.squid1.Cart.ShoppingCartAdapter
import com.example.squid1.Cart.ShoppingCartFragment
import com.example.squid1.Login.AuthManagement
import com.example.squid1.ProductAdapter
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavFragment : Fragment() {
    //fragment qui va gerer l'affichage de la page favoris
    private lateinit var apiService: APIService
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    lateinit var favView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        var view = inflater.inflate(R.layout.fragment_fav, container, false) //injection de la view

        favView = view.findViewById(R.id.fav_recyclerView) as RecyclerView
        primaryFunction()
        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun primaryFunction() {
        lifecycleScope?.launch {
            getUserfav()
        }
    }

    fun refreshFragment(shoppingCartFragment: ShoppingCartFragment) {
        fragmentManager?.beginTransaction()?.detach(shoppingCartFragment)?.commit()
        fragmentManager?.beginTransaction()?.attach(shoppingCartFragment)?.commit()
    }

    private lateinit var productAdapter: ProductAdapter
    private var favitem = listOf<FavItem>() //recuperation de la list favoris

    private fun getUserfav() {

        favView.layoutManager = //agencement par deux produit par ligne comme dans le fragment acceuil
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )

        apiService =
            activity?.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!

        val token = AuthManagement.getToken(requireActivity())
        val jwt = token?.let { JWT(it) }

        var userId = jwt?.getClaim("id")?.asString().toString()

        apiService.getUserFav(userId, jwt.toString()).enqueue(object : Callback<List<FavItem>> { //recupereration par l'api des favoris de l'utilisateur
            override fun onFailure(call: Call<List<FavItem>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<FavItem>>, response: Response<List<FavItem>>) { //affichage de la liste des produits present dans les favoris

                favitem = response.body()!!
                val products = favitem.map { favItem -> favItem.article }

                productAdapter = activity?.let { ProductAdapter(it, it, products) }!!

                favView.adapter = productAdapter

                productAdapter.notifyDataSetChanged()

            }

        })


    }


}



