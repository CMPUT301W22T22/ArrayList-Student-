package com.arrayliststudent.qrhunt;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class QRCodeActivityTest {
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
    public void codeListTest(){
        solo.waitForActivity(ConsoleActivity.class, 2000);
        solo.clickOnText("Generate Code");
        solo.clickOnText("View Codes"); //Click Scan Code Button
        // Asserts that the current activity is the ScanCodeActivity. Otherwise, show “Wrong Activity”
        assertTrue(solo.waitForActivity(CodeListActivity.class, 2000));
        solo.assertCurrentActivity("Wrong Activity", CodeListActivity.class);

        UserDataModel model = UserDataModel.getInstance();
        List<Map> codes = model.getCurrentUser().getUserCodeList();
        String codeName = new String();
        for (Map<String, Object> m : codes) {
            for (Map.Entry<String, Object> entry : m.entrySet()){
                if (entry.getKey().equals("codeName")) {
                    codeName = (String) entry.getValue();
                }
            }
            break;
        }
        assertTrue(solo.waitForText(codeName, 1, 500));
        solo.clickOnText(codeName);
        assertTrue(solo.waitForActivity(QRCodeActivity.class, 2000));
        solo.assertCurrentActivity("Wrong Activity", QRCodeActivity.class);

        Context context = solo.getCurrentActivity();

        codeName = ((QRCodeActivity)context).code.getCodeName();
        assertTrue(solo.waitForText(codeName, 1, 500));
        String score = String.valueOf(((QRCodeActivity)context).code.getCodeScore());

        assertTrue(solo.waitForText(score, 1, 500));

        List<Double> loc = ((QRCodeActivity)context).code.getLocation();
        String locString = loc.toString();
        assertTrue(solo.waitForText(locString, 1, 500));

    }
}
