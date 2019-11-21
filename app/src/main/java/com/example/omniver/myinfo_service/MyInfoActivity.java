package com.example.omniver.myinfo_service;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.example.omniver.R;
import com.example.omniver.base.BottomNavigationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MyInfoActivity extends BottomNavigationActivity {
    private BottomNavListener bottomNavListener;
    private BottomNavigationView navView;
    private FirstRankFragment firstRankFragment;
    private SecondRankFragment secondRankFragment;
    private ThirdRankFragment thirdRankFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
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
        firstRankFragment = new FirstRankFragment();
        secondRankFragment = new SecondRankFragment();
        thirdRankFragment = new ThirdRankFragment();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager();
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }
    public void setupViewPager(){
        pagerAdapter.addFragment(firstRankFragment,"1순위");
        pagerAdapter.addFragment(secondRankFragment,"2순위");
        pagerAdapter.addFragment(thirdRankFragment,"3순위");
        viewPager.setAdapter(pagerAdapter);
    }
}
