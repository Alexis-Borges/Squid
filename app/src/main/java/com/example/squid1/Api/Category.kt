package com.example.squid1.Api

import com.example.squid1.Image
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val category: Int,
    @SerializedName("name")
    val name: String,
)

