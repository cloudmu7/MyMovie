package com.cloud7mu7.mymovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

        loadDB();
    }

    void loadDB(){
        items.clear();
        diaryAdapter.notifyDataSetChanged();

        new Thread(){
            public void run(){
                String serverUrl = "http://cloudmu7777.dothome.co.kr/MyMovie/loadDB.php";

                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line = reader.readLine();
                    }

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            new AlertDialog.Builder(DiaryActivity.this).setMessage(buffer.toString()).create().show();
//                        }
//                    });

                    String data = buffer.toString();

                    String[] rows = data.split("@");
                    for(String row : rows){
                        String[] datas = row.split(",");
                        if(datas.length !=4) continue;

                        int no = Integer.parseInt(datas[0]);
                        String title = datas[1];
                        String day = datas[2];
                        String moviename = datas[3];
                        String coment = datas[4];

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Item item = new Item(no, title, day, moviename, coment);
                                items.add(0,item);
                                diaryAdapter.notifyItemInserted(0);
                            }
                        });
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void clickDiaryEdit(View view) {
        Intent intent = new Intent(this, DiaryWriteActivity.class);
        startActivity(intent);
    }
}