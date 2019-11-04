package com.example.omniver.main_service;

import com.example.omniver.model.Climate;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mainView;
    private MainInteractor mainInteractor;

    public MainPresenter(MainContract.View mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }


    public void getWeatherData(){
        mainInteractor.readWeatherData();
    }

    @Override
    public void setView(MainContract.View view) {
        this.mainView = view;
    }

    @Override
    public void releaseView() {
    }
}
