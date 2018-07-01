package com.alohagoha.cityinfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class CoatOfArmsActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation ==  ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if(savedInstanceState==null) {
            CoatOfArmsFragment details = new CoatOfArmsFragment();
            details.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(android.R.id.content,details).commit();
        }
    }
}
