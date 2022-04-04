package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HashMap<String,User> hashMap = new HashMap<>();
    private String codeID;
    private UserListAdapter userListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent intent = getIntent();
        recyclerView = findViewById(R.id.user_list_view);
        codeID = (String) intent.getStringExtra("ID");
        userListAdapter = new UserListAdapter(this,new ArrayList<User>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(), DividerItemDecoration.HORIZONTAL
        ));

        HighScorePresenter highScorePresenter = new HighScorePresenter();
        highScorePresenter.setListener(new HighScorePresenter.OnGetDataListener() {
            @Override
            public void refreshRecyclerView(HashMap<String, User> userHashMap) {
                hashMap = userHashMap;
                for (User user:hashMap.values()){
                    for (Map map:user.getUserCodeList()){
                        if (map.get("id").equals(codeID)){
                            userListAdapter.add(user);
                        }
                    }
                }
            }
        });
        highScorePresenter.UpdateData();
    }

    /*
    recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), bookList.get(position).getTitle() + " is clicked!", Toast.LENGTH_SHORT).show();
            }
        }));
     */

}