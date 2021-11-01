package com.cloud7mu7.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoxOfficeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    private String API_KEY = "a38d36f65eb1f8dea8fdc1dffffcfdb2";
    private final SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);

        recyclerView = findViewById(R.id.boxoffice_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        retrofitInterface.getBoxOffice(API_KEY, dateFm.format(cal.getTime())).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                BoxOfficeResult boxOfficeResult = result.getBoxOfficeResult();
                Log.d("retrofit", "Data fetch success");
                mAdapter = new MovieAdapter(boxOfficeResult.getDailyBoxOfficeList());

                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

}
