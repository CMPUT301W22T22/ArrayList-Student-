package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView userList;
    private TextView totalUserBase;
    private TextInputEditText searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userList = findViewById(R.id.user_list_view);
        totalUserBase = findViewById(R.id.total_userbase_view);
        searchbar = findViewById(R.id.search_bar);
    }


}