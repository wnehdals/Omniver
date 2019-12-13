package com.example.omniver.evaluate_service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.omniver.R;
import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.main_service.MainActivity;
import com.example.omniver.model.Climate;
import com.example.omniver.model.Picture;

import io.realm.Realm;

import static com.example.omniver.main_service.MainActivity.tempAverage;
public class EvaluationActivity extends BottomNavigationActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener,EvaluationContract.View {
    private ImageView photo;
    private String imagePath;
    private RatingBar upRatingbar;
    private Button saveButton;
    private Realm realm;
    private float grade;
    private EvaluationInteractor evaluationInteractor;
    private EvaluationPresenter evaluationPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        photo = (ImageView)findViewById(R.id.photo_image_view);
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("imagePath");
        Glide.with(this).load(imagePath).into(photo);
        init();

    }
    public void init(){
        grade = 0;
        saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        upRatingbar = (RatingBar)findViewById(R.id.ratingbar_1);
        upRatingbar.setOnRatingBarChangeListener(this);
        evaluationInteractor = new EvaluationInteractor();
        evaluationPresenter = new EvaluationPresenter(this,evaluationInteractor);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_button:
                final String timeStamp = getIntent().getStringExtra("date");
                evaluationPresenter.requestSave(timeStamp, tempAverage, imagePath, grade);
        }


    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        grade = v;
        Log.e("ratingbar",Float.toString(v));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void executeSave(boolean state) {
        if(state){
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EvaluationActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "저장에 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
    }
}
