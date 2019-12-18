package com.example.omniver;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.omniver.main_service.MainActivity;
import com.example.omniver.model.Picture;
import com.example.omniver.myinfo_service.MyInfoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class ClothesLogActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Picture> clothes;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_log);

        init();
    }

    private void init() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_center);
        TextView title = findViewById(R.id.actionbar_title);
        title.setText("입은 옷 기록");

        recyclerView = findViewById(R.id.clothes_card_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        clothes = getPictureList();
        Collections.reverse(clothes);
        adapter = new ClothesCardRecyclerAdapter(clothes);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Picture> getPictureList() {
        ArrayList<Picture> list = new ArrayList<>();

        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Picture> results = realm.where(Picture.class).findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return list;
    }
}
