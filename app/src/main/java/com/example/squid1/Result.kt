package com.example.squid1

import com.example.squid1.Api.Product
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("products")
    var products: List<Product> = listOf()
)