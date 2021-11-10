package com.cloud7mu7.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    ArrayList<Item> items = new ArrayList<>();

    RecyclerView recyclerView;
    DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        recyclerView = findViewById(R.id.diary_recycler);
        diaryAdapter = new DiaryAdapter(this, items);
        recyclerView.setAdapter(diaryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void loadDB(){
        items.clear();
        diaryAdapter.notifyDataSetChanged();
    }

    public void clickDiaryEdit(View view) {
        Intent intent = new Intent(this, DiaryWriteActivity.class);
        startActivity(intent);
    }
}