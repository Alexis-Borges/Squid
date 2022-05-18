package com.example.squid1.Api

import com.example.squid1.Image
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id_category")
    val category: Long,
    @SerializedName("name")
    val name: String,
)
