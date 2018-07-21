package com.alohagoha.weather1a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.res.Configuration;
import android.graphics.Bitmap;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.dmoral.toasty.Toasty;

public class BaseActivity extends AppCompatActivity
        implements BaseView.View, BaseFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    //инициализация переменных
    private FloatingActionButton fab;
    private TextView textView;
    private static final String TEXT = "TEXT";
    private static String contry;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            TextView tv = findViewById(R.id.tvUsername);
            contry = savedInstanceState.getString("NAME");
        }
        setContentView(R.layout.activity_base);

        initLayout();
    }


    private void initLayout() {
        //устанавливает тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //устанавливаем drawer (выездное меню)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //анимация клавищи (три палочки сверху) выездного меня
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //инициализация навигации
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); 

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new CreateActionFragment());
            }
        });

        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.ivProfile);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new android.widget.PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.avatar_settings);
                popupMenu.show();
            }
        });


        //addFragment(new WeatherFragment());
        startWeatherFragment(contry);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            addFragment1(new CreateActionFragment());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        outState.putString("NAME", ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUsername)).getText().toString());
        super.onSaveInstanceState(outState);
    }


    private void addFragment(Fragment fragment) {
        //вызываем SupportFragmentManager и указываем в каком контейнере будет находиться наш фрагмент
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                //.addToBackStack("")
                .commit();
    }

    private void addFragment1(Fragment fragment) {
        //вызываем SupportFragmentManager и указываем в каком контейнере будет находиться наш фрагмент
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame2, fragment)
                //.addToBackStack("")
                .commit();
    }


    private Fragment getCurrentFragment() {
        //получаем наименование фрагмента находящегося в контейнере в данных момент
        return getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    @Override
    public void onBackPressed() {
        //закрываем drawer если он был открыт при нажатии на аппаратную клавишу назад
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getCurrentFragment() instanceof CreateActionFragment) {
            addFragment(new WeatherFragment());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_info:
                return true;
            case R.id.action_category:
                return true;
            case R.id.action_select:
                if (isChecked) {
                    isChecked = false;
                    item.setChecked(isChecked);
                } else {
                    isChecked = true;
                    item.setChecked(isChecked);
                }
                return true;
            case R.id.action_one:
                item.setChecked(true);
                return true;
            case R.id.action_two:
                item.setChecked(true);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    //работаем с навигацией

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Toasty.success(this, "This is menu!").show();
        if (id == R.id.nav_settings) {
            // Handle the camera action
        } else if (id == R.id.nav_info) {
            // Handle the camera action
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Boolean inNetworkAvailable() {
        return true;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {
        Toasty.success(this, "Init drawer!!").show();
    }

    @Override
    public void onFragmentAttached() {


    }

    @Override
    public void onFragmentDetached(String tag) {

    }


    public void startWeatherFragment(String country) {
        //запускаем WeatherFragment и передаем туда country
        addFragment(WeatherFragment.newInstance(country));
        //cntry = country;


    }

    public Fragment getAnotherFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content_frame);

    }
}
