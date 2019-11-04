package com.example.omniver.main_service;

import android.os.Bundle;

import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BottomNavigationActivity implements MainContract.View{
    private BottomNavListener bottomNavListener;

    private BottomNavigationView navView;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavListener = new BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
    }
    protected void init(){
        mainPresenter = new MainPresenter(this,new MainInteractor());
        mainPresenter.setView(this);
        mainPresenter.getWeatherData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
