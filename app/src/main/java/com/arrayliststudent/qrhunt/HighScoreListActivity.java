package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class HighScoreListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private HighScorePresenter presenter;
    private HighScoreAdapter adapter;
    private ArrayList<User> userArrayList;
    private Boolean rankMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);
        recyclerView = findViewById(R.id.ScoreList);
        userArrayList = new ArrayList<>();
        User userTest = new User("Test","TestUserName");
        userArrayList.add(userTest);
        adapter = new  HighScoreAdapter(this,userArrayList);
        presenter = new HighScorePresenter();
        iniOnGetDataListener(presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL
        ));
        presenter.UpdateData();
    }

    public void RankButton(View view){
        if (rankMode){
            rankMode = false;
            RankList(false);
        }
        else{
            rankMode = true;
            RankList(true);
        }
    }

    public void RankList(Boolean RankMode){
        Collections.sort(userArrayList, new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                int i;
                if(RankMode){
                    i = user.getTotalScore()-t1.getTotalScore();
                }else {
                    i = t1.getTotalScore()-user.getTotalScore();
                }
                if (i == 0){
                    return 1;
                }
                return i;
            }
        });
    }

    public void iniOnGetDataListener(HighScorePresenter presenter){
        presenter.setListener(new HighScorePresenter.OnGetDataListener() {
            @Override
            public void refreshRecyclerView(HashMap<String, User> userHashMap) {
                    Collection<User> collection = userHashMap.values();
                    userArrayList = new ArrayList<>(collection);
                    adapter = new HighScoreAdapter(getApplicationContext(),userArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(defaultItemAnimator);
                    recyclerView.addItemDecoration(new DividerItemDecoration(
                            getApplicationContext(), DividerItemDecoration.HORIZONTAL
                    ));
            }
        });
    }
}