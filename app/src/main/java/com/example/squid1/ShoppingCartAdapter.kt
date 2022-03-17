package com.example.squid1

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.squid.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.cart_list_item.view.product_image
import kotlinx.android.synthetic.main.cart_list_item.view.product_name
import kotlinx.android.synthetic.main.cart_list_item.view.product_price

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bindItem(cartItems[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: CartItem) {


           // Picasso.get().load(cartItem.product.photos[0].filename).fit().into(itemView.product_image)
            Picasso.get().load("https://lh3.googleusercontent.com/9WyCZqTYQilYrcz6HvuGxT53molItl9gK-5rZn-FS_mgHgz4bN_z4ytjPTxcKb6Opr-JAN1_-ZQpFvb3pboCArCPR3ms6iJC4AJzuQ=w600").fit().into(itemView.product_image)

            itemView.product_name.text = cartItem.product.name

            itemView.product_price.text = "$${cartItem.product.price}"

            itemView.product_quantity.text = cartItem.quantity.toString()

        }


    }

}