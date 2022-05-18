package com.example.squid1.Api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/articles")
    fun getProducts(
//        @Query("offset") offset: Int,
//        @Query("limit") limit: Int
    ): Call<List<Product>>

    @GET("/category")
    fun getCategorys(
        @Query("id_category") offset: Long,

    ): Call<List<Category>>
}
