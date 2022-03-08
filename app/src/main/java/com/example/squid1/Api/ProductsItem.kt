package com.example.squid1.Api


import com.google.gson.annotations.SerializedName

data class ProductsItem(
    @SerializedName("color")
    val color: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favorites")
    val favorites: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<Any>,
    @SerializedName("name")
    val name: String,
    @SerializedName("orders")
    val orders: List<Any>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("ratings")
    val ratings: List<Any>,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("subCategories")
    val subCategories: List<Any>
)

