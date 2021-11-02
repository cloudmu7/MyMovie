package com.cloud7mu7.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBox(View view) {
        Intent intent = new Intent(this, BoxOfficeActivity.class);
        startActivity(intent);
    }

    public void clickMap(View view) {
        Intent intent = new Intent(this, CinemaMapActivity.class);
        startActivity(intent);
    }

    public void clickDiary(View view) {
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }
}