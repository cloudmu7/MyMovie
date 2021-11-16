package com.cloud7mu7.mymovie;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CinemaMapActivity extends AppCompatActivity {

    //검색어
    String searchQuery = "영화관";

    //내 위치
    Location mylocation;
    FusedLocationProviderClient locationProviderClient;

    SearchLocalApiResponse searchLocalApiResponse;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_map);



        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        int checkResult = checkSelfPermission(permissions[0]);
        if (checkResult == PackageManager.PERMISSION_DENIED) requestPermissions(permissions, 10);
        else requestMyLocation();



//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            int checkResult = checkCallingPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            if(checkResult==PackageManager.PERMISSION_DENIED){
//                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
//                requestPermissions(permissions, 0);
//            }
//        }

    }



    void requestMyLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //실시간 위치검색 조건 설정
        LocationRequest request = LocationRequest.create();
        request.setInterval(1000); //위치정보 갱신 간격
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //우선순위

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper());

    }

    //위치정보 받았을때 반응하는 객체 생성
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            mylocation = locationResult.getLastLocation();

            Toast.makeText(CinemaMapActivity.this, ""+mylocation.getLatitude(), Toast.LENGTH_SHORT).show();

            locationProviderClient.removeLocationUpdates(locationCallback);

            searchPlace();
        }
    };

    void searchPlace(){

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://dapi.kakao.com");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitInterface retrofitService = retrofit.create(RetrofitInterface.class);
        Call<SearchLocalApiResponse> call = retrofitService.searchPlace(searchQuery, mylocation.getLongitude()+"", mylocation.getLatitude()+"");
        call.enqueue(new Callback<SearchLocalApiResponse>() {
            @Override
            public void onResponse(Call<SearchLocalApiResponse> call, Response<SearchLocalApiResponse> response) {
                searchLocalApiResponse = response.body();

                showmap();

            }

            @Override
            public void onFailure(Call<SearchLocalApiResponse> call, Throwable t) {
                Toast.makeText(CinemaMapActivity.this, "서버 오류입니다.\n잠시 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showmap(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.cinemaFm);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

//                LatLng nowplace = new LatLng(37.560787455939014, 127.03466122564981);
                LatLng nowplace = new LatLng(mylocation.getLatitude(), mylocation.getLongitude());


                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowplace, 16));

                if (ActivityCompat.checkSelfPermission(CinemaMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CinemaMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }
                googleMap.setMyLocationEnabled(true);

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);

                for (Place place : searchLocalApiResponse.documents){
                    double latitode = Double.parseDouble(place.y);
                    double longitude = Double.parseDouble(place.x);
                    LatLng position = new LatLng(latitode, longitude);

                    //마커 옵션 객체를 통해 마커의 설정
                    MarkerOptions options = new MarkerOptions().position(position).title(place.place_name).snippet(place.distance + "m");
                    googleMap.addMarker(options).setTag(place.place_url);
                }

                MarkerOptions marker = new MarkerOptions();
                marker.position(nowplace);
                marker.title("현재 위치");
                marker.snippet("테스트 문구");

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        Intent intent = new Intent(CinemaMapActivity.this, PlaceUrlActivity.class);
                        intent.putExtra("place_url", marker.getTag().toString());
                        startActivity(intent);
                    }
                });

            }
        });
    }
}