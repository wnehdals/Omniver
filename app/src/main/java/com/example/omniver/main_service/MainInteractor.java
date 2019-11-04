package com.example.omniver.main_service;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainInteractor {
    gpsTracker = new GpsTracker(MainActivity.this);

    latitude = gpsTracker.getLatitude();
    longitude = gpsTracker.getLongitude();
    //String lat = "36.78";
    //String lot = "127.31";
        Log.e("latitude",Double.toString(latitude));
        Log.e("longitute",Double.toString(longitude));

    Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

    ApiInterface service = client.create(ApiInterface.class);
    Call<Repo> call = service.repo("aa6922d8cb10d11756abb9a69fa3649e", Double.valueOf(latitude), Double.valueOf(longitude));
        call.enqueue(new Callback<Repo>() {
        @Override
        public void onResponse(Call<Repo> call, Response<Repo> response) {
            if (response.isSuccessful()) {
                repo = response.body();
                tempAverage = repo.getMain().getTemp()-273.15;
                tempMin = repo.getMain().getTemp_min()-273.15;
                tempMax = repo.getMain().getTemp_max()-273.15;
                Log.e("현재 온도",Double.toString(tempAverage));
                Log.e("현재 위치",repo.getName());
                updateWeather(tempAverage);
            } else {

            }
        }

        @Override
        public void onFailure(Call<Repo> call, Throwable t) {

        }

    });
}
