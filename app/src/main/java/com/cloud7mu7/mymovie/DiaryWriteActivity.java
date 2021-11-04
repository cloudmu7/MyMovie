package com.cloud7mu7.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DiaryWriteActivity extends AppCompatActivity {

    EditText etTitle, etMoviename, etComent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        etTitle = findViewById(R.id.diary_title_et);
        etMoviename = findViewById(R.id.diary_moviename_et);
        etComent = findViewById(R.id.diary_coment_et);
    }

    public void clickSave(View view){

        new Thread(){
            @Override
            public void run() {
                String title = etTitle.getText().toString();
                String moviename = etMoviename.getText().toString();
                String coment = etComent.getText().toString();

                String serverUrl = "http://cloudmu7777.dothome.co.kr/MyMovie/insertDB.php";

                try {
                    URL url = new URL(serverUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    String data = "title=" + title + "&moviename=" + moviename + "&coment=" + coment;

                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(os);
                    writer.write(data, 0, data.length());
                    writer.flush();
                    writer.close();

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line!=null){
                        buffer.append(line+"/n");
                        line = reader.readLine();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DiaryWriteActivity.this, buffer.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }.start();

    }
}