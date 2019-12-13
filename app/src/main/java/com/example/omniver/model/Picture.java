package com.example.omniver.model;

import io.realm.Realm;
import io.realm.RealmObject;

import static com.example.omniver.main_service.MainActivity.tempAverage;

public class Picture extends RealmObject {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private float grade;
    private double temperature;
    String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public float getGrade() {
        return grade;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }



}
