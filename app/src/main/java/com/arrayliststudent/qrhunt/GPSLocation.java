package com.arrayliststudent.qrhunt;

import android.Manifest;
import android.content.Context;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class GPSLocation {
    private LocationManager locationManager;
    public double[] deviceLocation = {0, 0};

    public GPSLocation(Context context) {
        String serviceString = context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) context.getSystemService(serviceString);

        String provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null){
            deviceLocation[0] = location.getLatitude();
            deviceLocation[1] = location.getLongitude();
        }
        else {
            deviceLocation[0] = 0;
            deviceLocation[0] = 0;
        }
    }

    public double[] getDeviceLocation() {
        System.out.println(deviceLocation[0]+" "+deviceLocation[1]);
        return deviceLocation;
    }
}
