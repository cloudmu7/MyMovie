package com.cloud7mu7.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CinemaMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_map);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.cinemaFm);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng nowplace = new LatLng(37.560797, 127.034571);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowplace, 15));

                MarkerOptions marker = new MarkerOptions();
                marker.position(nowplace);
                marker.title("현재 위치");
                marker.snippet("테스트 문구");
//                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action));

                googleMap.addMarker(marker);

                UiSettings settings = googleMap.getUiSettings();
                settings.setZoomControlsEnabled(true);

                settings.setMyLocationButtonEnabled(true);

                if (ActivityCompat.checkSelfPermission(CinemaMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CinemaMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                googleMap.setMyLocationEnabled(true);


            }
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int checkResult = checkCallingPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if(checkResult==PackageManager.PERMISSION_DENIED){
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, 0);
            }
        }

    }


}