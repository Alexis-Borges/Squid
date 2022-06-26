package com.example.squid1.Login

import retrofit2.http.POST
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body

interface API { //Requete API pour la connexion et l'inscription
    @POST("auth/inscription")
    fun createUser(
        @Body user: User?
    ): Call<ResponseBody?>?

    @POST("auth/connection")
    fun checkUser(
        @Body user: User?
    ): Call<ResponseBody?>?
}