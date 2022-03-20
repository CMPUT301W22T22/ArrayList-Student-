package com.arrayliststudent.qrhunt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GPSLocationActivity extends AppCompatActivity {
    private LocationManager locationManager;
    public double[] deviceLocation = {0,0};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(GPSLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(GPSLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(GPSLocationActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                deviceLocation[0] = location.getLatitude();
                deviceLocation[1] = location.getLongitude();
                getDeviceLocation();
            }
        });
    }

    public double[] getDeviceLocation() {
        System.out.println(deviceLocation[0]+" "+deviceLocation[1]);
        return deviceLocation;
    }
}
