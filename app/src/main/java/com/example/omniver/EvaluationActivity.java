package com.example.omniver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.omniver.base.BottomNavigationActivity;

public class EvaluationActivity extends BottomNavigationActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    private ImageView photo;
    private String imagePath;
    private RatingBar upRatingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        photo = (ImageView)findViewById(R.id.photo_image_view);
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("imagePath");
        //String timeStamp = intent.getStringExtra("date");
        Log.e("주소",imagePath);
        Glide.with(this).load(imagePath).into(photo);
        init();

    }
    public void init(){
        upRatingbar = (RatingBar)findViewById(R.id.ratingbar_1);
        upRatingbar.setOnRatingBarChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        Log.e("ratingbar",Float.toString(v));
    }
}
