package com.arrayliststudent.qrhunt;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class UserProfileActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<UserProfileActivity> rule =
            new ActivityTestRule<>(UserProfileActivity.class,true,true);
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void profileTest(){
        solo.assertCurrentActivity("Correct Activity",UserProfileActivity.class);
        View fab = rule.getActivity().findViewById(R.id.floatingActionButton);
        solo.clickOnView(fab);
        solo.clickOnButton("Cancel");
        solo.clickOnButton("Remove");
        solo.clickOnButton("Cancel");
    }
}
