package com.example.omniver;

import android.os.Bundle;

import com.example.omniver.base.BottomNavigationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyInfoActivity extends BottomNavigationActivity {
    private BottomNavListener bottomNavListener;
    private BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        bottomNavListener = new BottomNavigationActivity.BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
    }
}
