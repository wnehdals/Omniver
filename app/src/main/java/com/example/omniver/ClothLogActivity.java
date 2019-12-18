package com.example.omniver;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.main_service.MainActivity;
import com.example.omniver.myinfo_service.MyInfoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ClothLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_log);

        init();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu1:
                        Intent intent1 = new Intent(ClothLogActivity.this, CategoryActivity.class);
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.navigation_menu2:
                        Intent intent2 = new Intent(ClothLogActivity.this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.navigation_menu3:
                        Intent intent3 = new Intent(ClothLogActivity.this, MyInfoActivity.class);
                        startActivity(intent3);
                        finish();
                        return true;
                }
                return true;
            }
        });
    }

    private void init() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_center);
        TextView title = findViewById(R.id.actionbar_title);
        title.setText("입은 옷 기록");
    }
}
