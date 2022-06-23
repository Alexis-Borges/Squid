package com.example.squid1.Api


import android.util.JsonToken
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
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
        @Query("id") id: Int
    ): Call<List<Product>>

    //Cart
    @POST("carts/increment")
    fun addToShoppingCart(
        @Query("idCustomer") userId: String,
        @Query("idArticle") id: Int,
        @Header("token") Authentication: String
    ): Call<ResponseBody>

    @GET("carts/byCustomer")
    fun getUserProductFromShoppingCart(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String
    ): Call<List<Cartitem>>

    @POST("carts/decrement")
    fun deleteAProductFromShoppingCart(
        @Query("idCustomer") userId: String,
        @Query("idArticle") id: Int,
        @Header("token") Authentication: String
    ): Call<ResponseBody>

    //payments
    @POST("create-payment-intent")
    fun pay(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String
    ): Call<JsonObject>

    //Fav
    @GET("favorites")
    fun getUserFav(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String
    ): Call<List<FavItem>>

    @POST("favorites")
    fun addToFavList(
        @Query("idCustomer") userId: String,
        @Query("idArticle") id: Int,
        @Header("token") Authentication: String
    ): Call<ResponseBody>
}

