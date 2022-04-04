package com.arrayliststudent.qrhunt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ConsoleActivityTest {
    private Solo solo;
    private LocationManager locationManager;
    public double[] deviceLocation = {0, 0};

    @Rule
    public ActivityTestRule<MapsActivity> rule  =
            new ActivityTestRule<>(MapsActivity.class,true,true);
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void mapsTest(){
        Context context = solo.getCurrentActivity();
        solo.assertCurrentActivity("Activity initializtion failed",MapsActivity.class);
        //assert Map is successfully opened.
        assertTrue(solo.waitForLogMessage("Map is ready!",10));
        String serviceString = context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) context.getSystemService(serviceString);
        String provider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null){
            deviceLocation[0] = location.getLatitude();
            deviceLocation[1] = location.getLongitude();
        }
        else {
            deviceLocation[0] = 0;
            deviceLocation[1] = 0;
        }
        assertNotEquals(deviceLocation, new double[]{0, 0});
        assertNotNull(deviceLocation);
    }
    @Test
    public void locationPermissionTest(){
        Context context = solo.getCurrentActivity();
        // assert geolocation permissions are granted.
        assertEquals(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION), PackageManager.PERMISSION_GRANTED);
        assertEquals(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION),PackageManager.PERMISSION_GRANTED);
    }
}
