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
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Product
import com.example.squid1.Login.AuthManagement
import com.google.android.material.internal.ContextUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_row_item.view.*
import kotlinx.android.synthetic.main.product_row_item.view.addToCart
import kotlinx.android.synthetic.main.product_row_item.view.product_image
import kotlinx.android.synthetic.main.product_row_item.view.product_name
import kotlinx.android.synthetic.main.product_row_item.view.product_price
import kotlinx.android.synthetic.main.product_row_item.view.product_stock
import kotlinx.android.synthetic.main.product_row_item.view.removeItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var apiService: APIService

class ProductAdapter(
    var context: Context,
    act: Activity,
    var products: List<Product> = arrayListOf()
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private val activity = act
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        //injection des informations produits dans un design
        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Savoir quel product a été sélectionner grace à sa position dans la liste
        viewHolder.bindProduct(products[position], activity)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")//assignation des valeurs produits aux id de la view
        fun bindProduct(product: Product, activity: Activity) {

            itemView.product_name.text = product.name
            itemView.product_price.text = "${product.price.toString()} €"
            itemView.product_stock.text = "${product.stock.toString()} - Restant"


            var imgWhiteBorderHeart = itemView.findViewById<ImageView>(R.id.imgWhiteBorderHeart)


            Picasso.get().load(product.image[0].url).fit().into(itemView.product_image)
            //Onclick événement pour incrementer au panier un produit à partir d'un bouton appelé addtocart
            itemView.addToCart.setOnClickListener {

                val token = AuthManagement.getToken(activity)
                val jwt = token?.let { JWT(it) }

                var userId = jwt?.getClaim("id")?.asString().toString()
                apiService =
                    APIConfig.getRetrofitClient(itemView.context)
                        .create(APIService::class.java)
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
            //Même chose que l'ajout mais cette fois on le décrémente
            itemView.removeItem.setOnClickListener {

                val token = AuthManagement.getToken(activity)
                val jwt = token?.let { JWT(it) }

                var userId = jwt?.getClaim("id")?.asString().toString()

                apiService = activity.let {
                    APIConfig.getRetrofitClient(it).create(APIService::class.java)
                }!!

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
                                "Le Produit a bien été retiré",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Error", t.message.toString())
                            Toast.makeText(itemView.context, "Error !", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
            }
            //Événement OnClick pour l'ajout aux favoris de l'utilisateur
            itemView.imgWhiteBorderHeart.setOnClickListener {

                val token = AuthManagement.getToken(activity)
                val jwt = token?.let { JWT(it) }

                var userId = jwt?.getClaim("id")?.asString().toString()

                apiService = activity.let {
                    APIConfig.getRetrofitClient(it).create(APIService::class.java)
                }!!

                APIConfig.getRetrofitClient(itemView.context).create(APIService::class.java)
                apiService.addToFavList(
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
                                "Ce Produit a bien été ajouté a vos Favoris",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Error", t.message.toString())
                            Toast.makeText(itemView.context, "Error !", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
            }
                //TODO retirer un produit de ces favoris à partir de la vue favoris
//            itemView.imgWhiteFilledHeart.setOnClickListener {
//
//                val token = AuthManagement.getToken(activity)
//                val jwt = token?.let { JWT(it) }
//
//                var userId = jwt?.getClaim("id")?.asString().toString()
//
//                apiService = activity.let {
//                    APIConfig.getRetrofitClient(it).create(APIService::class.java)
//                }
//
//                APIConfig.getRetrofitClient(itemView.context).create(APIService::class.java)
//                apiService.delFromFavList(
//                    userId,
//                    product.id,
//                    jwt.toString()
//                )
//                    .enqueue(object : Callback<ResponseBody> {
//                        @SuppressLint("RestrictedApi")
//                        override fun onResponse(
//                            call: Call<ResponseBody>,
//                            response: Response<ResponseBody>
//                        ) {
//                            Toast.makeText(
//                                itemView.context,
//                                "Ce Produit a bien été retiré de vos Favoris",
//                                Toast.LENGTH_SHORT
//                            )
//                                .show()
//
//                        }
//
//                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                            Log.e("Error", t.message.toString())
//                            Toast.makeText(itemView.context, "Error !", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//
//                    })
//            }

            var wichImg: Int
            wichImg = 2
            itemView.product_image.setOnClickListener { // Permets de changer d'image en appuyant sur une image produit
                when (wichImg) {
                    1 -> {
                        Picasso.get().load(product.image[0].url).into(itemView.product_image)
                        wichImg = 2
                    }
                    2 -> {
                        if (product.image.size >= 2) {
                            Picasso.get().load(product.image[1].url).into(itemView.product_image)
                            wichImg = 3
                        } else {
                            Picasso.get().load(product.image[0].url).into(itemView.product_image)
                            wichImg = 2
                        }
                    }
                    3 -> {
                        if (product.image.size >= 3) {
                            Picasso.get().load(product.image[2].url).into(itemView.product_image)
                            wichImg = 4
                        } else {
                            Picasso.get().load(product.image[0].url).into(itemView.product_image)
                            wichImg = 2
                        }
                    }
                    4 -> {
                        if (product.image.size >= 4) {
                            Picasso.get().load(product.image[3].url).into(itemView.product_image)
                            wichImg = 1
                        } else {
                            Picasso.get().load(product.image[0].url).into(itemView.product_image)
                            wichImg = 2
                        }
                    }
                }
            }

        }

    }
}
