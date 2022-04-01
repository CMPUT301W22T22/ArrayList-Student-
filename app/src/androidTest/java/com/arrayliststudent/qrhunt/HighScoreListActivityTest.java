package com.arrayliststudent.qrhunt;

import static org.junit.Assert.*;

import android.widget.AbsListView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.HashMap;

public class HighScoreListActivityTest {
    @Test
    public void onCreate() {
        HighScorePresenter presenter = new HighScorePresenter();
        presenter.setListener(new HighScorePresenter.OnGetDataListener() {
            @Override
            public void refreshRecyclerView(HashMap<String, User> userHashMap) {
                Solo solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                        new ActivityTestRule<>(HighScoreListActivity.class, true, true).getActivity());
                assertTrue(userHashMap==null);
            }
        });

    }
}