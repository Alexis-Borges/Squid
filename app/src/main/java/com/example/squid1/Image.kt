package com.example.squid1

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url")
    var filename: String? = null
)