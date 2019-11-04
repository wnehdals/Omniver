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

    public Climate readWeatherData(double latitude, double longitude){
        final Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<Climate> call = service.repo("aa6922d8cb10d11756abb9a69fa3649e", Double.valueOf(latitude), Double.valueOf(longitude));
        call.enqueue(new Callback<Climate>() {
            @Override
            public void onResponse(Call<Climate> call, Response<Climate> response) {
                Log.e("readWeatherdata","if");
                if (response.isSuccessful()) {
                    climate = response.body();
                    double tempAverage = climate.getMain().getTemp()-273.15;    //켈빈에서 섭씨로 바꾸는 작업
                    double tempMin = climate.getMain().getTemp_min()-273.15;
                    double tempMax = climate.getMain().getTemp_max()-273.15;

                    climate.getMain().setTemp(tempAverage);                     //섭씨값으로 수정
                    climate.getMain().setTemp_min(tempMin);
                    climate.getMain().setTemp_max(tempMax);
                    //Log.e("현재 온도",Double.toString(tempAverage));
                    //Log.e("현재 위치",repo.getName());
                } else {
                    Log.e("readWeatherdata","else");
                }
            }

            @Override
            public void onFailure(Call<Climate> call, Throwable t) {
                    Log.e("fali","fail");
            }

        });
        return climate;
    }
    public void print(){
        Log.e("interactor","print");
    }





}
