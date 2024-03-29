package com.example.omniver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.TextView;

public class RecommendedListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    final int[][] icons = {
            {R.mipmap.sleeveless, R.mipmap.shorts},
            {R.mipmap.shorts, R.mipmap.casual_t_shirt_},
            {R.mipmap.long_sleeves_t_shirt, R.mipmap.hoodie},
            {R.mipmap.neat, R.mipmap.hoodie},
            {R.mipmap.black_jacket, R.mipmap.long_sleeves_t_shirt},
            {R.mipmap.coat, R.mipmap.blouse_with_lace},
            {R.mipmap.coat, R.mipmap.scarf},
            {R.mipmap.winter, R.mipmap.black_jacket}
    };
    final String[] texts = {
            "나시티, 반바지, 민소매 원피스",
            "반팔, 얇은 셔츠, 얇은 긴팔, 반바지, 면바지",
            "긴팔티, 가디건, 후드티, 면바지, 슬랙스, 스키니",
            "니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스",
            "자켓, 셔츠, 가디건, 간절기 야상, 살색스타킹",
            "트렌치코트, 간절기 야상, 여러겹 껴입기",
            "코트, 가죽자켓",
            "겨울 옷(야상, 패딩, 목도리 등)"
    };
    final String[] temperatures1 = {"27", "23", "20", "17", "12", "10", "6", "0"};
    final String[] temperatures2 = {"99", "26", "22", "19", "16", "11", "9", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_list);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecommendedListRecyclerAdapter(icons, texts, temperatures1, temperatures2);
        recyclerView.setAdapter(mAdapter);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_center);
        TextView title = findViewById(R.id.actionbar_title);
        title.setText("기온별 옷차림");
    }
}
