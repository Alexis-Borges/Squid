package com.example.squid1

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("filename")
    var filename: String? = null
)