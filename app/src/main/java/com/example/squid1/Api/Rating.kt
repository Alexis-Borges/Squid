package com.example.squid1.Api

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("id")
    val id: Long,
    @SerializedName("rating")
    val rating: Float,
)
