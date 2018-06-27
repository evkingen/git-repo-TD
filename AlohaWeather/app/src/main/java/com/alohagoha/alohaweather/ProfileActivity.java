package com.alohagoha.alohaweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    private EditText et;
    private FloatingActionButton fab;
    private static final String TEXT = "TEXT";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        et = findViewById(R.id.et);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity();
            }
        });
    }

    private void startNewActivity() {
        Intent intent = new Intent(this,WeatherActivity.class);
        intent.putExtra(TEXT,et.getText().toString().trim());
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
       startNewActivity();
    }
}
