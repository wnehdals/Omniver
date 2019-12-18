package com.example.omniver.main_service;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.omniver.CategoryActivity;
import com.example.omniver.GpsTracker;
import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.R;
import com.example.omniver.model.Climate;
import com.example.omniver.myinfo_service.MyInfoActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private TextView descriptionTextView;
    private TextView humidityTextView;
    private TextView windSpeedTextView;
    private MainPresenter mainPresenter;
    private GpsTracker gpsTracker;
    private Climate climate;
    private MainInteractor mainInteractor;
    private TextView currentPlaceInfoTextView;
    public static double tempAverage;
    private CollapsingToolbarLayout collaspingLayout;
    private ImageView weatherIcon;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private TextView lowestTempTextView;
    private TextView averageTempTextView;
    private TextView highestTempTextView;
    private Realm realm;
    private TextView recommendUpText;
    private TextView recommendDownText;
    private ImageView recommendUpImageView;
    private ImageView recommendDownImageView;
    private ImageView miniWeatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_menu1:
                    Intent intent1 = new Intent(MainActivity.this, CategoryActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_menu2:
                    return true;
                case R.id.navigation_menu3:
                    Intent intent3 = new Intent(MainActivity.this, MyInfoActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return true;
            }
        });

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }
        init();
    }

    protected void init() {
        lowestTempTextView = (TextView) findViewById(R.id.lowest_temp);
        averageTempTextView = (TextView) findViewById(R.id.average_temp);
        highestTempTextView = (TextView) findViewById(R.id.highest_temp);
        currentPlaceInfoTextView = (TextView) findViewById(R.id.current_place_info);
        descriptionTextView = (TextView) findViewById(R.id.description);
        humidityTextView = (TextView) findViewById(R.id.humidity);
        windSpeedTextView = (TextView) findViewById(R.id.windspeed);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);
        collaspingLayout = (CollapsingToolbarLayout) findViewById(R.id.collasing_toolbar);
        //temperatureText = (TextView)findViewById(R.id.temp);
        mainInteractor = new MainInteractor();
        mainPresenter = new MainPresenter(this, mainInteractor);
        realm.init(this);
        recommendUpImageView = (ImageView) findViewById(R.id.recommend_cloth_up);
        recommendDownImageView = (ImageView) findViewById(R.id.recommend_cloth_down);
        recommendDownText = (TextView) findViewById(R.id.recommend_cloth_down_text);
        recommendUpText = (TextView) findViewById(R.id.recommend_cloth_up_text);
        miniWeatherIcon = (ImageView)findViewById(R.id.mini_weather_icon);
        //mainPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        currentPlaceInfoTextView.setText(getCurrentAddress(latitude, longitude));
        Log.d("MainActivity", Double.toString(latitude));
        Log.d("MainActivity", Double.toString(longitude));
        mainPresenter.getWeatherData(latitude, longitude);
        //readWeatherData(latitude,longitude);
        //getWeather(latitude,longitude);

        super.onStart();
    }

    @Override
    public void onReceiveClimateData(Climate climateData) {
        climate = climateData;
        tempAverage = climate.getMain().getTemp();
        descriptionTextView.setText("오늘 날씨 : " + climate.getWeather().get(0).getMainWeather());
        humidityTextView.setText("습도 : " + Integer.toString(climate.getMain().getHumidity()));
        windSpeedTextView.setText("풍속 : " + Double.toString(climate.getWind().getSpeed())+ "m/s");
        //Glide.with(this).load("http://openweathermap.org/img/wn/10d@2x.png").into(weatherIcon);
        weatherIconSelect(climate.getWeather().get(0).getIcon());
        String minTemp = String.format("%.1f", climate.getMain().getTemp_min());
        String temp = String.format("%.1f", climate.getMain().getTemp());
        String maxTemp = String.format("%.1f", climate.getMain().getTemp_max());
        lowestTempTextView.setText(minTemp);
        averageTempTextView.setText(temp);
        highestTempTextView.setText(maxTemp);
        recommendClothImageView();
        recommendClothText();
        //collaspingLayout.setBackgroundResource(R.drawable.ic_logo);
        //temperatureText.setText(Double.toString(climate.getMain().getTemp()));
        //Log.d("onRecieve", Double.toString(climate.getMain().getTemp()));
    }

    public void recommendClothText() {
        if (tempAverage > 26.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 나시티, 반바지, 민소매입니다");
        } else if (23.0 <= tempAverage && tempAverage <= 26.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 반팔, 반바지, 얇은 셔츠입니다");
        } else if (20.0 <= tempAverage && tempAverage < 23.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 긴팔티, 면바지, 후드티입니다");
        } else if (17.0 <= tempAverage && tempAverage < 20.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 니트, 맨투맨, 면바지입니다");
        } else if (12.0 <= tempAverage && tempAverage < 17.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 자켓, 야상입니다");
        } else if (10.0 <= tempAverage && tempAverage < 12.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 트렌치코트, 야상, 여러겹 입기입니다");
        } else if (6.0 <= tempAverage && tempAverage < 10.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 코트, 가죽자켓입니다");
        } else if (tempAverage < 6.0) {
            recommendUpText.setText("오늘 추천드릴 옷은 패딩, 목도리입니다");
        }
    }

    public void recommendClothImageView() {
        if (tempAverage > 26.0) {
            Glide.with(this).load(R.mipmap.sleeveless).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.shorts).into(recommendDownImageView);
        } else if (23.0 <= tempAverage && tempAverage <= 26.0) {
            Glide.with(this).load(R.mipmap.casual_t_shirt_).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.shorts).into(recommendDownImageView);
        } else if (20.0 <= tempAverage && tempAverage < 23.0) {
            Glide.with(this).load(R.mipmap.long_sleeves_t_shirt).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.long_pants).into(recommendDownImageView);
        } else if (17.0 <= tempAverage && tempAverage < 20.0) {
            Glide.with(this).load(R.mipmap.neat).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.long_pants).into(recommendDownImageView);
            recommendUpText.setText("오늘 추천드릴 옷은 니트, 맨투맨, 면바지입니다");
        } else if (12.0 <= tempAverage && tempAverage < 17.0) {
            Glide.with(this).load(R.mipmap.black_jacket).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.hoodie).into(recommendDownImageView);
            recommendUpText.setText("오늘 추천드릴 옷은 자켓, 야상입니다");
        } else if (10.0 <= tempAverage && tempAverage < 12.0) {
            Glide.with(this).load(R.mipmap.hoodie).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.coat).into(recommendDownImageView);
            recommendUpText.setText("오늘 추천드릴 옷은 트렌치코트, 야상, 여러겹 입기입니다");
        } else if (6.0 <= tempAverage && tempAverage < 10.0) {
            Glide.with(this).load(R.mipmap.coat).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.black_jacket).into(recommendDownImageView);
            recommendUpText.setText("오늘 추천드릴 옷은 코트, 가죽자켓입니다");
        } else if (tempAverage < 6.0) {
            Glide.with(this).load(R.mipmap.winter).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.scarf).into(recommendDownImageView);
            recommendUpText.setText("오늘 추천드릴 옷은 패딩, 목도리입니다");
        }
    }

    public void weatherIconSelect(String description) {
        String iconType = description.replace("n", "d");
        Log.d("iconType", iconType);
        Glide.with(this).load("http://openweathermap.org/img/wn/" + iconType + "@2x.png").into(weatherIcon);
        Glide.with(this).load("http://openweathermap.org/img/wn/" + iconType + "@2x.png").into(miniWeatherIcon);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {


            boolean check_result = true;



            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public String getCurrentAddress(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
    }


    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:

                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
