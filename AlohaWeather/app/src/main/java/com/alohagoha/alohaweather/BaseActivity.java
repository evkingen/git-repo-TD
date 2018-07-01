package com.alohagoha.alohaweather;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseView.View, BaseFragment.CallBack {

    public void addFragment() {
        getSupportFragmentManager()

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public Boolean isNetworkAvailable() {
        return null;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {

    }
}
