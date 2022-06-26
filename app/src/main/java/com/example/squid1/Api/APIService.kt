package com.example.squid1.Api


import android.util.JsonToken
import androidx.datastore.preferences.protobuf.ListValue
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//Appel et Route utilisé dans l'application pour communiquer avec L'Api
interface APIService {
    //Produits
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
    //Produits

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
    //Cart

    //Paiements
    @POST("create-payment-intent")
    fun pay(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String
    ): Call<JsonObject>
    //Paiements

    //Orders
    @POST("confirmOrder")
    fun ConfirmedOrder(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String
    ): Call<ResponseBody>

    @GET("order/byCustomer")
    fun OrdersbyCustumers(
        @Query("idCustomer") userId: String,
        @Header("token") Authentication: String
    ): Call<ResponseBody>
    //Orders

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
    //Fav
    //contact
    @POST("contact")
    fun contactinfo(
        @Body userContact: UserContact,
    ): Call<ResponseBody>
}

