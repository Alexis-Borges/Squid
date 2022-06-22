package com.example.squid1.Api

import okhttp3.Response
import okhttp3.ResponseBody
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

    //paniers
    @POST("carts/increment")
    fun addToShoppingCart(
        @Query("idCustomer") userId: String,
        @Query("idArticle") id: Int,
        @Header("Authentication") Authentication: String): Call<ResponseBody>

    @GET("carts/byCustomer")
    fun getUserProductFromShoppingCart(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String): Call<List<Cartitem>>

    @POST("carts/decrement")
    fun deleteAProductFromShoppingCart(
        @Query("idCustomer") userId: String,
        @Query("idArticle") id: Int,
        @Header("Authentication") Authentication: String): Call<ResponseBody>
}

