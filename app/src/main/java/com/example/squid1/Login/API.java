package com.example.squid1.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("auth/inscription")
    Call<ResponseBody> createUser (
            @Body User user
    );

    @POST("auth/connection")
    Call<ResponseBody> checkUser (
            @Body User user
    );

}
