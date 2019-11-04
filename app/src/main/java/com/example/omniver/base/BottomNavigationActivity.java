package com.example.omniver.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.omniver.CategoryActivity;
import com.example.omniver.MyInfoActivity;
import com.example.omniver.R;
import com.example.omniver.main_service.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        */

    }
     public class BottomNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_menu1:
                    Intent intent1 = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_menu2:
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_menu3:
                    Intent intent3 = new Intent(getApplicationContext(), MyInfoActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    }

}
