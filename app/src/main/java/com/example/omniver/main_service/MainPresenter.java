package com.example.omniver.main_service;

import android.util.Log;

import com.example.omniver.model.Climate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mainView;
    private MainInteractor mainInteractor;
    private Climate climate;

    public MainPresenter(MainContract.View mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }


    public void getWeatherData(double latitude, double longitude){
     mainInteractor.readWeatherData(latitude,longitude,new Callback<Climate>() {
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
                    mainView.onReceiveClimateData(climate);
                } else {
                    Log.e("readWeatherdata","else");
                }
            }

            @Override
            public void onFailure(Call<Climate> call, Throwable t) {
                Log.e("fali","fail");
            }

        });


    }

    @Override
    public void setView(MainContract.View view) {
        this.mainView = view;
    }

    @Override
    public void releaseView() {
    }
}
