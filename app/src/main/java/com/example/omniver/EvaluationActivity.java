package com.example.omniver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.omniver.base.BottomNavigationActivity;

public class EvaluationActivity extends BottomNavigationActivity {
    ImageView photo;
    private String imagePath;
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

    }
}
