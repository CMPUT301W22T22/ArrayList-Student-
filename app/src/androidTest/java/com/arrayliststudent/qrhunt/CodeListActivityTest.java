package com.arrayliststudent.qrhunt;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CodeListActivityTest  {
    private Solo solo;


    @Rule
    public ActivityTestRule<CodeListActivity> rule  =
            new ActivityTestRule<>(CodeListActivity.class,true,true);
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        UserDataModel model = UserDataModel.getInstance();
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        model.fetchCurrentUser(Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                model.setUserId(android_id);
                model.setUserCodes();
            }
        }, 3500);

    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void mapsTest(){
        Context context = solo.getCurrentActivity();
        solo.assertCurrentActivity("Activity initializtion failed",CodeListActivity.class);
        //assert Map is successfully opened.
        assertTrue(solo.waitForLogMessage("Map is ready!",10));

//        assertNotEquals(deviceLocation, new double[]{0, 0});
//        assertNotNull(deviceLocation);
    }
    @Test
    public void locationPermissionTest(){

//        assertEquals(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION), PackageManager.PERMISSION_GRANTED);
//        assertEquals(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION),PackageManager.PERMISSION_GRANTED);
    }
}
