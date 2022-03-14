package com.arrayliststudent.qrhunt;

import static org.junit.Assert.assertEquals;

import android.widget.EditText;
import android.widget.ImageView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author Kieran
 * Test for launching comment activity and fragment from launch
 */

@RunWith(AndroidJUnit4.class)
public class CommentActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<QRCodeActivity> mainRule  =
            new ActivityTestRule<>(QRCodeActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), mainRule.getActivity());
    }

    /**
     * Demonstrates functionality of comment activity by navigating to it and adding a comment
     */
    @Test
    public void addComment(){
        //start in console activity
        solo.assertCurrentActivity("Hopefully console", QRCodeActivity.class);
        //solo.clickOnImage(1);
        //solo.assertCurrentActivity("Not QRCode page", QRCodeActivity.class);
        //ImageView image = solo.getImage(4);
        solo.clickOnImage(3);
        solo.assertCurrentActivity("Wrong activity", CommentsActivity.class);
        solo.clickOnButton("Add Comment");
        String comment = "This is a comment.";
        solo.enterText((EditText) solo.getView(R.id.comment_frag), comment);
        solo.clickOnButton("OK");
        solo.waitForText(comment, 1, 1000);
        CommentsActivity activity = (CommentsActivity) solo.getCurrentActivity();
        ArrayList<Comment> comments = activity.comments;
        assertEquals(comment, comments.get(0).getBody());
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
