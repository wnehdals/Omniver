package com.example.omniver.main_service;

import com.example.omniver.model.Climate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/data/2.5/weather")
    Call<Climate> repo(@Query("appid") String appid, @Query("lat") double lat, @Query("lon") double lon);


}
