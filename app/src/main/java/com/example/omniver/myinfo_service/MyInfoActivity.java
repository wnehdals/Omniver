package com.example.omniver.myinfo_service;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import androidx.viewpager.widget.ViewPager;

import com.example.omniver.CustomActionbar;
import com.example.omniver.R;
import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.model.Picture;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.omniver.main_service.MainActivity.tempAverage;

public class MyInfoActivity extends BottomNavigationActivity {
    private BottomNavListener bottomNavListener;
    private BottomNavigationView navView;
    private FirstRankFragment firstRankFragment;
    private SecondRankFragment secondRankFragment;
    private ThirdRankFragment thirdRankFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Realm realm;
    private final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
    private List<Picture> pictureArrayList;
    private TreeMap<Double, Integer> pictureRankList;
    private String[] imgUrl;
    private int index[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        bottomNavListener = new BottomNavigationActivity.BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
        init();
    }
    public void init(){
        imgUrl = new String[3];
        index = new int[3];
        firstRankFragment = new FirstRankFragment();
        secondRankFragment = new SecondRankFragment();
        thirdRankFragment = new ThirdRankFragment();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pictureArrayList = getPictureList();
        pictureRankList = new TreeMap<Double, Integer>(Collections.reverseOrder());
        setRank(pictureArrayList);
        setupViewPager();

    }
    public void setupViewPager(){
        boolean zeroFlag = false;
        boolean firstFlag = false;
        boolean secondFlag = false;
        boolean thridFlag = false;
        if(pictureArrayList.size() == 0){
            zeroFlag = true;
        }else if(pictureArrayList.size() == 1){
            zeroFlag = true;
            firstFlag = true;
        }else if(pictureArrayList.size() == 2){
            zeroFlag = true;
            firstFlag = true;
            secondFlag = true;
        }else if(pictureArrayList.size()>=3){
            zeroFlag = true;
            firstFlag = true;
            secondFlag = true;
            thridFlag = true;
        }

        if( firstFlag){
            Bundle firstBundle = new Bundle();
            firstBundle.putString("img",pictureArrayList.get(index[0]).getImageUrl());
            firstBundle.putString("date",pictureArrayList.get(index[0]).getYear()+"."+pictureArrayList.get(index[0]).getMonth()+"."+
                    pictureArrayList.get(index[0]).getDay());
            firstBundle.putFloat("grade",pictureArrayList.get(index[0]).getGrade());
            firstBundle.putDouble("temp",pictureArrayList.get(index[0]).getTemperature());
            firstRankFragment.setArguments(firstBundle);
        }
        if(secondFlag){
            Bundle secondBundle = new Bundle();
            secondBundle.putString("img",pictureArrayList.get(index[1]).getImageUrl());
            secondBundle.putString("date",pictureArrayList.get(index[1]).getYear()+"."+pictureArrayList.get(index[1]).getMonth()+"."+
                    pictureArrayList.get(index[1]).getDay());
            secondBundle.putFloat("grade",pictureArrayList.get(index[1]).getGrade());
            secondBundle.putDouble("temp",pictureArrayList.get(index[1]).getTemperature());
            secondRankFragment.setArguments(secondBundle);
        }
        if(thridFlag){
            Bundle thridBundle = new Bundle();
            thridBundle.putString("img",pictureArrayList.get(index[2]).getImageUrl());
            thridBundle.putString("date",pictureArrayList.get(index[2]).getYear()+"."+pictureArrayList.get(index[2]).getMonth()+"."+
                    pictureArrayList.get(index[2]).getDay());
            thridBundle.putFloat("grade",pictureArrayList.get(index[2]).getGrade());
            thridBundle.putDouble("temp",pictureArrayList.get(index[2]).getTemperature());
            thirdRankFragment.setArguments(thridBundle);
        }

        pagerAdapter.addFragment(firstRankFragment,"1순위");
        pagerAdapter.addFragment(secondRankFragment,"2순위");
        pagerAdapter.addFragment(thirdRankFragment,"3순위");
        viewPager.setAdapter(pagerAdapter);
    }
    public List<Picture> getPictureList() {
        List<Picture> list = new ArrayList<>();

        try {
            realm = Realm.getDefaultInstance();/*
            final RealmResults<Picture> result = realm.where(Picture.class).findAll();
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    result.deleteAllFromRealm();
                }
            });
            */

            RealmResults<Picture> results = realm
                    .where(Picture.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        Log.e("realmcoujnt",Integer.toString(list.size()));
        return list;
    }

    double calculate(float grade, double temperature, int month){
        double result = 0;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        result = grade*10+(45-(Math.abs(tempAverage-temperature)))+(5-(Math.abs(month-Integer.parseInt(timeStamp.substring(4,6)))));
        Log.e("clal",Float.toString(grade*5)+"+"+Double.toString(45-(Math.abs(tempAverage-temperature)))+"+"+Integer.toString(5-(Math.abs(month-Integer.parseInt(timeStamp.substring(4,6))))));
        return result;
    }

    public void setRank(List<Picture> pictureArrayList){
        HashMap<Double,Integer> list = new HashMap<Double, Integer>();
        for(int i=0;i<pictureArrayList.size();i++){
            list.put(calculate(pictureArrayList.get(i).getGrade(),pictureArrayList.get(i).getTemperature(),pictureArrayList.get(i).getMonth()),i);
        }
        pictureRankList.putAll(list);
        Iterator<Double> iteratorKey = pictureRankList.keySet().iterator();
        for(int j=0;j<3;j++){
            try {
                double key = iteratorKey.next();
                index[j] = pictureRankList.get(key);

            }catch (NoSuchElementException e){
                e.printStackTrace();
            }

        }
    }



}
