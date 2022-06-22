package com.example.squid1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Product
import com.example.squid1.Api.listProductFavourite
import com.example.squid1.Login.AuthManagement
import com.google.android.material.internal.ContextUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.product_row_item.view.*
import kotlinx.android.synthetic.main.product_row_item.view.product_image
import kotlinx.android.synthetic.main.product_row_item.view.product_name
import kotlinx.android.synthetic.main.product_row_item.view.product_price
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var apiService: APIService

class ProductAdapter(var context: Context, act: Activity, var products: List<Product> = arrayListOf()) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private val activity = act
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bindProduct(products[position] , activity)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bindProduct(product: Product, activity: Activity) {

            itemView.product_name.text = product.name
            itemView.product_price.text = "${product.price.toString()} €"
            itemView.product_stock.text = "${product.stock.toString()} - Restant"


            Picasso.get().load(product.image[0].url).fit().into(itemView.product_image)

            itemView.addToCart.setOnClickListener {

                val token = AuthManagement.getToken(activity)
                val jwt = token?.let { JWT (it) }

                var userId = jwt?.getClaim("id")?.asString().toString()
                apiService =
                    APIConfig.getRetrofitClient(itemView.context).create(APIService::class.java)
                apiService.addToShoppingCart(userId, product.id, jwt.toString())
                    .enqueue(object :
                        Callback<ResponseBody> {
                        @SuppressLint("RestrictedApi")
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            Toast.makeText(
                                itemView.rootView.context,
                                product.name + " a bien été ajouté au panier",
                                Toast.LENGTH_SHORT
                            ).show()
                            val mainActivity =
                                ContextUtils.getActivity(itemView.context) as MainActivity

                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Error", t.message.toString())
                            Toast.makeText(
                                itemView.rootView.context,
                                "Erreur: Redémarrer l'application",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })

            }



            itemView.removeItem.setOnClickListener {

                val token = AuthManagement.getToken(activity)
                val jwt = token?.let { JWT (it) }

                var userId = jwt?.getClaim("id")?.asString().toString()

                apiService = activity.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!

                APIConfig.getRetrofitClient(itemView.context).create(APIService::class.java)
                apiService.deleteAProductFromShoppingCart(
                    userId,
                    product.id,
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
                                "Le Produit a bien été retirer",
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


            var imgWhiteBorderHeart = itemView.findViewById<ImageView>(R.id.imgWhiteBorderHeart)
            var imgWhiteHeart = itemView.findViewById<ImageView>(R.id.imgWhiteHeart)

            imgWhiteHeart.visibility = View.INVISIBLE

            val imgHeart = arrayOf(imgWhiteBorderHeart, imgWhiteHeart)
            imgWhiteBorderHeart.setOnClickListener {
                showHide(imgHeart)
                listProductFavourite.add(product)
            }
            imgWhiteHeart.setOnClickListener {
                showHide(imgHeart)
                listProductFavourite.remove(product)
            }

            if (product in listProductFavourite) {
                showHide(imgHeart)
            }

        }

        // Manage the toggle event on heart's article click
        fun showHide(imgViews: Array<ImageView>) {
            for (view in imgViews)
                view.visibility =
                    if (view.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
    }
}