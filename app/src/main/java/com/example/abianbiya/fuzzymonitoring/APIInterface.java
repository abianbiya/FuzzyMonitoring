package com.example.abianbiya.fuzzymonitoring;

/**
 * Created by Jim Geovedi on 3/30/2017.
 */


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    //-------------------akun----------------------

    @GET("login")
    Call<ResponseBody> login(
            @Query("username") String username,
            @Query("password") String password
    );

    //-----------------peternak-----------------------

    @GET("api.php")
    Call<ResponseBody> dataSatuan();

    @GET("api2.php")
    Call<ResponseBody> listData();

}