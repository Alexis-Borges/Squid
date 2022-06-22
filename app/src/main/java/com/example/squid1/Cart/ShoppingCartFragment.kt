package com.example.squid1.Cart

import android.os.Bundle
import com.example.squid.R
import kotlinx.android.synthetic.main.fragment_blank.*
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
import com.auth0.android.jwt.JWT
import com.example.squid.databinding.FragmentCartBinding
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Cartitem
import com.example.squid1.Login.AuthManagement
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShoppingCartFragment : Fragment() {

    private lateinit var apiService: APIService
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    lateinit var cartView: RecyclerView
    private lateinit var removeItemCart: ImageButton
    private lateinit var checkoutButton: Button
    private lateinit var totalCartPrice: TextView
    private var panierPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        var view = inflater.inflate(R.layout.fragment_cart, container, false)

        cartView = view.findViewById(R.id.shopping_cart_recyclerView) as RecyclerView
        totalCartPrice = view.findViewById(R.id.totalCartPrice) as TextView
        checkoutButton = view.findViewById(R.id.checkoutButton)
//        checkoutButton.setOnClickListener {
//            val intent = Intent(activity, ValidateShopping::class.java)
//            intent.putExtra("panierPrice", panierPrice)
//            startActivityForResult(intent, 1) //startActivityForResult(intent, 1)
//        }
        primaryFunction()
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1){
            if(resultCode == RESULT_OK){ //l'achat est bien passé
                refreshFragment()
            }
        }
    }

    private fun primaryFunction(){
        lifecycleScope?.launch{
            getUserShoppingCart()
        }
    }

    private fun refreshFragment(){
        fragmentManager?.beginTransaction()?.detach(this)?.commit()
    }

    private lateinit var shoppingCartAdapter: ShoppingCartAdapter
    private var cartitem = listOf<Cartitem>()

    private fun getUserShoppingCart() {


        cartView.layoutManager =
            LinearLayoutManager(context)

        apiService = activity?.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!

        val token = AuthManagement.getToken(requireActivity())
        val jwt = token?.let { JWT (it) }

        var userId = jwt?.getClaim("id")?.asString().toString()

        apiService.getUserProductFromShoppingCart(userId, jwt.toString()).enqueue(object : Callback<List<Cartitem>> {
            override fun onFailure(call: Call<List<Cartitem>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Cartitem>>, response: Response<List<Cartitem>>) {

                cartitem = response.body()!!

                var totalPrice = 0.0
                for (cartitem in cartitem) {
                    totalPrice += cartitem.article.price * cartitem.quantity

                }
                totalCartPrice.text = totalPrice.toString() + "€"

                shoppingCartAdapter = activity?.let { ShoppingCartAdapter(it, cartitem, it) }!!

                cartView.adapter = shoppingCartAdapter

                shoppingCartAdapter.notifyDataSetChanged()

            }

        })

    }
}

