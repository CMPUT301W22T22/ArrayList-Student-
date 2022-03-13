package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView userList;
    private TextView totalUserBase;
    private TextInputEditText searchbar;

    private CustomRVAdapter userListAdapter;
    private HashMap<Integer, User> userDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userDataList = UserDataModel.getInstance().userList;

        userList = findViewById(R.id.user_list_view);
        totalUserBase = findViewById(R.id.total_userbase_view);
        searchbar = findViewById(R.id.search_bar);


        userListAdapter = new UserListAdapter(, userDataList);
        totalUserBase.setText(userDataList.size());
        userList.setAdapter(new ListClickListener(), UserListAdapter);
    }

    //click listener for userList
    private class ListClickListener implements RecyclerView.RecyclerListener{
        @Override
        public void onItemClick(View view, int position){
            //do somethin
        }
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