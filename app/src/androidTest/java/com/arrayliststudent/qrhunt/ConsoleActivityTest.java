package com.arrayliststudent.qrhunt;

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
import android.service.autofill.UserData;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ConsoleActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule  =
            new ActivityTestRule<>(MainActivity.class,true,true);
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void consoleTest() {
        Context context = solo.getCurrentActivity();
        solo.assertCurrentActivity("Activity initialization failed", ConsoleActivity.class);
        UserDataModel model = UserDataModel.getInstance();
        String userName = model.getCurrentUser().getName();
        assertTrue(solo.waitForText(userName, 1, 500));
        String numCodes = String.valueOf(model.getCurrentUser().getNumCodes());
        assertTrue(solo.waitForText(numCodes, 1, 500));
        String totalScore = String.valueOf(model.getCurrentUser().getTotalScore());
        assertTrue(solo.waitForText(totalScore, 1, 500));

        solo.clickOnText("Generate Code");
        userName = model.getCurrentUser().getName();
        assertTrue(solo.waitForText(userName, 1, 500));
        numCodes = String.valueOf(model.getCurrentUser().getNumCodes());
        assertTrue(solo.waitForText(numCodes, 1, 500));
        totalScore = String.valueOf(model.getCurrentUser().getTotalScore());
        assertTrue(solo.waitForText(totalScore, 1, 500));


    }
    @Test
    public void codesButtonTest() {
        solo.waitForActivity(ConsoleActivity.class, 2000);

        solo.clickOnText("View Codes"); //Click Scan Code Button
        // Asserts that the current activity is the ScanCodeActivity. Otherwise, show “Wrong Activity”
        assertTrue(solo.waitForActivity(CodeListActivity.class, 2000));
        solo.assertCurrentActivity("Wrong Activity", CodeListActivity.class);
    }

}
