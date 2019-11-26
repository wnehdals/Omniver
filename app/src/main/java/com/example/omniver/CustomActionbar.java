package com.example.omniver;

import androidx.appcompat.app.ActionBar;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.Toolbar;


public class CustomActionbar {
    private Activity activity;
    private ActionBar actionBar;

    public CustomActionbar(Activity activity, ActionBar actionBar) {
        this.activity = activity;
        this.actionBar = actionBar;
    }

    public void setActionBar(){
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View customView = LayoutInflater.from(activity).inflate(R.layout.toolbar_base,null);
        actionBar.setCustomView(customView);
    }
}
