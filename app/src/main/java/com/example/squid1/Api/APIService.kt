package com.example.squid1.Api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/articles/get/")
    fun getProducts(
//        @Query("offset") offset: Int,
//        @Query("limit") limit: Int
    ): Call<List<Product>>

}
