package com.example.squid1.Cart

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Cartitem
import com.example.squid1.Login.AuthManagement
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.cart_list_item.view.product_image
import kotlinx.android.synthetic.main.cart_list_item.view.product_name
import kotlinx.android.synthetic.main.cart_list_item.view.product_price
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var apiService: APIService

class ShoppingCartAdapter(var context: Context, var cartItems: List<Cartitem>, act: Activity) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    private val activity = act

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bindItem(cartItems[position], activity)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: Cartitem, activity: Activity) {

            Picasso.get().load(cartItem.article.images[0].url).fit().into(itemView.product_image)

            itemView.product_name.text = cartItem.article.name

            itemView.product_price.text = "${cartItem.article.price} €"

            itemView.product_quantity.text = cartItem.quantity.toString()

            itemView.removeItemCart.setOnClickListener { view ->

                apiService = activity.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!
                val token = AuthManagement.getToken(activity)
                val jwt = token?.let { JWT(it) }

                var userId = jwt?.getClaim("id")?.asString().toString()

                APIConfig.getRetrofitClient(itemView.context).create(APIService::class.java)

                apiService.deleteAProductFromShoppingCart(
                    userId,
                    cartItem.article.id,
                    jwt.toString()
                )
                    .enqueue(object : Callback<ResponseBody> {
                        @SuppressLint("RestrictedApi")
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            Toast.makeText(
                                itemView.context,
                                "Un Exemplaire de l'article a bien été retirer",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Error", t.message.toString())
                            Toast.makeText(itemView.context, "Error !", Toast.LENGTH_SHORT).show()
                        }

                    })

            }


        }
    }
}