package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * Activity which displays the list of all active users.
 *
 * @author jmgraham
 */
public class UserListActivity extends AppCompatActivity {
    private RecyclerView userList;
    private TextInputEditText searchbar;
    private TextView userBase;

    private ArrayList<User> userDataList;
    private ArrayAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userList = findViewById(R.id.user_list_view);
        searchbar = findViewById(R.id.search_bar);
        userBase = findViewById(R.id.total_userbase_view);

        userAdapter = new UserList(this, userDataList);
        userList.setAdapter(userAdapter);
    }
}