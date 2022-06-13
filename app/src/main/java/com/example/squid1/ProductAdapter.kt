package com.example.squid1

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.squid.R
import com.example.squid1.Api.Product
import com.example.squid1.Api.listProductFavourite
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.product_row_item.view.*

class ProductAdapter(var context: Context, var products: List<Product> = arrayListOf()) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bindProduct(products[position])

    // (context as MainActivity).coordinator

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bindProduct(product: Product) {

            itemView.product_name.text = product.name
            itemView.product_price.text = "${product.price.toString()} €"
            itemView.product_stock.text = "${product.stock.toString()} - Restant"

             Picasso.get().load("https://lh3.googleusercontent.com/9WyCZqTYQilYrcz6HvuGxT53molItl9gK-5rZn-FS_mgHgz4bN_z4ytjPTxcKb6Opr-JAN1_-ZQpFvb3pboCArCPR3ms6iJC4AJzuQ=w600").fit().into(itemView.product_image)
//            Picasso.get().load(product.image[0].filename).fit().into(itemView.product_image)

            //                val products = mutableListOf<Product>()
//                products.add(product)
//

//                ShoppingCart.addItem(item)


            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                itemView.addToCart.setOnClickListener { view ->

                    val item = CartItem(product)

                    ShoppingCart.addItem(item)
                    //notify users
                    Snackbar.make(
                        (itemView.context as MainActivity).coordinator,
                        "${product.name} added to your cart",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(ShoppingCart.getCart())

                }

                itemView.removeItem.setOnClickListener { view ->

                    val item = CartItem(product)

                    ShoppingCart.removeItem(item, itemView.context)

                    Snackbar.make(
                        (itemView.context as MainActivity).coordinator,
                        "${product.name} removed from your cart",
                        Snackbar.LENGTH_LONG
                    ).show()
                    it.onNext(ShoppingCart.getCart())

                }


            }).subscribe { cart ->

                var quantity = 0

                cart.forEach { cartItem ->

                    quantity += cartItem.quantity
                }

                (itemView.context as MainActivity).cart_size.text = quantity.toString()
//                Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()

//            }
//                Toast.makeText(itemView.context, "${product.name} added to your cart", Toast.LENGTH_SHORT).show()


            }

            var imgBlackBorderHeart = itemView.findViewById<ImageView>(R.id.imgBlackBorderHeart)
            var imgRedHeart = itemView.findViewById<ImageView>(R.id.imgRedHeart)

            // Manage the toggle event on heart's article click
            fun showHide(imgViews: Array<ImageView>) {
                for (view in imgViews)
                    view.visibility =
                        if (view.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            }

            val imgHeart = arrayOf(imgBlackBorderHeart, imgRedHeart)
            imgBlackBorderHeart.setOnClickListener {
                showHide(imgHeart)
                listProductFavourite.add(product)
            }
            imgRedHeart.setOnClickListener {
                showHide(imgHeart)
                listProductFavourite.remove(product)
            }

            if (product in listProductFavourite) {
                showHide(imgHeart)
            }

        }

    }}