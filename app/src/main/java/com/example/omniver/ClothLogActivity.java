package com.example.omniver;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.TextView;

import com.example.omniver.base.BottomNavigationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClothLogActivity extends BottomNavigationActivity {
    private BottomNavListener bottomNavListener;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_log);

        bottomNavListener = new BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
        init();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_center);
        TextView title = findViewById(R.id.actionbar_title);
        title.setText("입은 옷 기록");
    }

    private void init() {

    }
}
