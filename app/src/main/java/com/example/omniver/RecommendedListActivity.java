package com.example.omniver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RecommendedListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    String[] clothes = {"나시티, 반바지, 민소매 원피스", "반팔, 얇은 셔츠, 얇은 긴팔, 반바지, 면바지", "긴팔티, 가디건, 후드티, 면바지, 슬랙스, 스키니", "니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스", "자켓, 셔츠, 가디건, 간절기 야상, 살색스타킹", "트렌치코트, 간절기 야상, 여러겹 껴입기", "코트, 가죽자켓", "겨울 옷(야상, 패딩, 목도리 등)" };
    String[] temperatures1 = { "27", "23", "20", "17", "12", "10", "6", "0"};
    String[] temperatures2 = { "99", "26", "22", "19", "16", "11", "9", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_list);

        recyclerView = findViewById(R.id.recycler);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter = new RecyclerAdapter(clothes, temperatures1, temperatures2);
        recyclerView.setAdapter(mAdapter);
    }
}
