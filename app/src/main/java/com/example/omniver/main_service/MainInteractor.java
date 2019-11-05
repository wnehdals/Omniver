package com.example.omniver.main_service;

import android.util.Log;

import com.example.omniver.GpsTracker;
import com.example.omniver.model.Climate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainInteractor {
    private Climate climate;

    public MainInteractor() {
    }

    public void readWeatherData(double latitude, double longitude, Callback<Climate> callback) {
        final Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<Climate> call = service.repo("aa6922d8cb10d11756abb9a69fa3649e", Double.valueOf(latitude), Double.valueOf(longitude));
        call.enqueue(callback);
    }



}
