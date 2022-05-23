package com.example.squid1.Api

//import com.google.gson.annotations.Expose
import com.example.squid1.Image
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("category")
    val category: Category,
    @SerializedName("color")
    val color: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val image: List<Image> = arrayListOf(),
    @SerializedName("name")
    val name: String,
    @SerializedName("orders")
    val orders: List<Any>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("stock")
    val stock: Int,
)