package com.example.omniver.myinfo_service;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.omniver.R;


public class SecondRankFragment extends Fragment {

    private ImageView imageView;
    private String imagePath;
    private String dateInfo;
    private Float userGrade;
    private TextView dateTextView;
    private TextView gradeTextView;
    private TextView tempTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_rank, container, false);
        imageView = (ImageView)view.findViewById(R.id.my_second_picture);
        dateTextView =  (TextView)view.findViewById(R.id.second_date);
        gradeTextView = (TextView)view.findViewById(R.id.second_grade);
        tempTextView = (TextView)view.findViewById(R.id.second_temp);

        Bundle bundle = getArguments();
        if(bundle != null) {
            imagePath = bundle.getString("img");
            Glide.with(this).load(imagePath).into(imageView);
            tempTextView.setText("# " + String.format("%.1f", bundle.getDouble("temp")) + "°C");
            gradeTextView.setText("# " + Float.toString((bundle.getFloat("grade"))) + "점");
            dateTextView.setText("# " + bundle.getString("date"));
        }
        return view;
    }


}
