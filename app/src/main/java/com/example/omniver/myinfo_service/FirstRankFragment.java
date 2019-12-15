package com.example.omniver.myinfo_service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.omniver.CategoryActivity;
import com.example.omniver.R;


public class FirstRankFragment extends Fragment {

    private ImageView imageView;
    private String imagePath;
    private String dateInfo;
    private Float userGrade;
    private TextView dateTextView;
    private TextView gradeTextView;
    private TextView tempTextView;
    private FrameLayout frameLayout;
    private Button initialButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_rank, container, false);
        imageView = (ImageView)view.findViewById(R.id.my_first_picture);
        dateTextView =  (TextView)view.findViewById(R.id.first_date);
        gradeTextView = (TextView)view.findViewById(R.id.first_grade);
        tempTextView = (TextView)view.findViewById(R.id.first_temp);
        frameLayout = (FrameLayout)view.findViewById(R.id.initial_display_1);
        initialButton = (Button)view.findViewById(R.id.initial_button_1);

        Bundle bundle = getArguments();
        if(bundle != null) {
            frameLayout.setVisibility(View.GONE);
            initialButton.setVisibility(View.GONE);
            imagePath = bundle.getString("img");
            Glide.with(this).load(imagePath).into(imageView);
            tempTextView.setText("# " + String.format("%.1f", bundle.getDouble("temp")) + "°C");
            gradeTextView.setText("# " + Float.toString((bundle.getFloat("grade"))) + "점");
            dateTextView.setText("# " + bundle.getString("date"));
        }else{
            frameLayout.setVisibility(View.VISIBLE);
            initialButton.setVisibility(View.VISIBLE);
            tempTextView.setVisibility(View.GONE);
            gradeTextView.setVisibility(View.GONE);
            dateTextView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }
        return view;
    }



}
