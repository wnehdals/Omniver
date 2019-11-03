package com.example.omniver;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BottomNavigationActivity {
    private BottomNavListener bottomNavListener;
    private BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavListener = new BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
    }

}
