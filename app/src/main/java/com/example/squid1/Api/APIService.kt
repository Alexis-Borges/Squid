package com.example.squid1.Api

import android.provider.ContactsContract
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/articles/trending")
    fun getProducts(
    ): Call<List<Product>>

    @GET("/category")
    fun getCategories(

    ): Call<List<Category>>

    @GET("/articles/getByCategory")
    fun getProductsByCategory(
        @Query ("id") id : Int
    ): Call<List<Product>>
}
