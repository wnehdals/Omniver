package com.example.omniver.main_service;

import android.util.Log;

import com.example.omniver.model.Climate;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mainView;
    private MainInteractor mainInteractor;
    private Climate climate;

    public MainPresenter(MainContract.View mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }


    public void getWeatherData(double latitude, double longitude){
        climate = mainInteractor.readWeatherData(latitude,longitude);
        Log.e("presenter",Double.toString(climate.getMain().getTemp()));
        mainView.onReceiveClimateData(climate);
    }

    @Override
    public void setView(MainContract.View view) {
        this.mainView = view;
    }

    @Override
    public void releaseView() {
    }
}
