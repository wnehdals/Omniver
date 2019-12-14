package com.example.omniver.evaluate_service;

import android.util.Log;

import com.example.omniver.model.Picture;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

import static com.example.omniver.main_service.MainActivity.tempAverage;

public class EvaluationInteractor {
    private Realm realm;
    private String timeStamp;
    private double tempAverage;
    private String imagePath;
    private float grade;
    private boolean state = false;


    public boolean processSave(String time, double temp, String imageurl, float rank) {
        realm = Realm.getDefaultInstance();
        timeStamp = time;
        tempAverage = temp;
        imagePath = imageurl;
        grade = rank;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Picture picture = realm.createObject(Picture.class);
                picture.setTemperature(tempAverage);
                picture.setImageUrl(imagePath);
                picture.setGrade(grade);
                picture.setYear(Integer.parseInt(timeStamp.substring(0, 4)));
                picture.setMonth(Integer.parseInt(timeStamp.substring(4, 6)));
                picture.setDay(Integer.parseInt(timeStamp.substring(6, 8)));
                picture.setHour(Integer.parseInt(timeStamp.substring(9, 11)));
                picture.setMinute(Integer.parseInt(timeStamp.substring(11, 13)));

            }
        });
        state = true;
        realm.close();
        return state;
    }


}

