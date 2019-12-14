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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.omniver.GpsTracker;
import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.R;
import com.example.omniver.model.Climate;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends BottomNavigationActivity implements MainContract.View {
    private BottomNavListener bottomNavListener;
    private TextView descriptionTextView;
    private TextView humidityTextView;
    private TextView windSpeedTextView;
    private BottomNavigationView navView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavListener = new BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
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
        //textView = (TextView)findViewById(R.id.temp);
        mainInteractor = new MainInteractor();
        mainPresenter = new MainPresenter(this, mainInteractor);
        realm.init(this);
        recommendUpImageView = (ImageView) findViewById(R.id.recommend_cloth_up);
        recommendDownImageView = (ImageView) findViewById(R.id.recommend_cloth_down);
        recommendDownText = (TextView) findViewById(R.id.recommend_cloth_down_text);
        recommendUpText = (TextView) findViewById(R.id.recommend_cloth_up_text);
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
        windSpeedTextView.setText("풍속 : " + Double.toString(climate.getWind().getSpeed()));
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
        //textView.setText(Double.toString(climate.getMain().getTemp()));
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
        } else if (0 <= tempAverage && tempAverage < 6.0) {
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
        } else if (0 <= tempAverage && tempAverage < 6.0) {
            Glide.with(this).load(R.mipmap.winter).into(recommendUpImageView);
            Glide.with(this).load(R.mipmap.scarf).into(recommendDownImageView);
            recommendUpText.setText("오늘 추천드릴 옷은 패딩, 목도리입니다");
        }
    }

    public void weatherIconSelect(String description) {
        String iconType = description.replace("n", "d");
        Log.d("iconType", iconType);
        Glide.with(this).load("http://openweathermap.org/img/wn/" + iconType + "@2x.png").into(weatherIcon);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

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
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;    //주의

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
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


    //여기부터는 GPS 활성화를 위한 메소드들
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

                //사용자가 GPS 활성 시켰는지 검사
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
        realm.close();
    }
}
