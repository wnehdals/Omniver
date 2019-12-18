package com.example.omniver.evaluate_service;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.omniver.CategoryActivity;
import com.example.omniver.R;
import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.main_service.MainActivity;
import com.example.omniver.myinfo_service.MyInfoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import static com.example.omniver.main_service.MainActivity.tempAverage;

public class EvaluationActivity extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener, EvaluationContract.View {
    private ImageView photo;
    private RatingBar upRatingbar;
    private Button saveButton;
    private String imagePath;
    private float grade;
    private EvaluationInteractor evaluationInteractor;
    private EvaluationPresenter evaluationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        photo = findViewById(R.id.photo_image_view);
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("imagePath");
        Glide.with(this).load(imagePath).into(photo);
        init();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu1:
                        Intent intent1 = new Intent(EvaluationActivity.this, CategoryActivity.class);
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.navigation_menu2:
                        Intent intent2 = new Intent(EvaluationActivity.this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.navigation_menu3:
                        Intent intent3 = new Intent(EvaluationActivity.this, MyInfoActivity.class);
                        startActivity(intent3);
                        finish();
                        return true;
                }
                return true;
            }
        });
    }

    public void init() {
        grade = 0;
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        upRatingbar = findViewById(R.id.ratingbar_1);
        upRatingbar.setOnRatingBarChangeListener(this);
        evaluationInteractor = new EvaluationInteractor();
        evaluationPresenter = new EvaluationPresenter(this, evaluationInteractor);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_center);
        TextView title = findViewById(R.id.actionbar_title);
        title.setText("평가");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:
                final String timeStamp = getIntent().getStringExtra("date");
                evaluationPresenter.requestSave(timeStamp, tempAverage, imagePath, grade);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        grade = v;
        Log.e("ratingbar", Float.toString(v));
    }

    @Override
    public void executeSave(boolean state) {
        if (state) {
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EvaluationActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "저장에 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
    }
}
