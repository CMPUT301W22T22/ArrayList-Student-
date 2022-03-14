package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView userList;
    private TextView totalUserBase;
    private TextInputEditText searchbar;

    private CustomRVAdapter userListAdapter;
    private ArrayList<User> userDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        //userDataList = new ArrayList<>(UserDataModel.getInstance().userList.values());
        userDataList = new ArrayList<>();
        userDataList = populate(userDataList);

        System.out.println("gdsgdsgds");
        Intent intent = new Intent();

        userList = findViewById(R.id.user_list_view);
        totalUserBase = findViewById(R.id.total_userbase_view);
        searchbar = findViewById(R.id.search_bar);

        userListAdapter = new UserListAdapter(new ListClickListener(), userDataList);
        System.out.println(userDataList.size());
        totalUserBase.setText(/*userDataList.size()*/"hewwo");
        userList.setAdapter(userListAdapter);

        totalUserBase.setText(getTotalUserText(userDataList.size()));
    }

    private ArrayList<User> populate(ArrayList<User> list){
        list.add(new User(11));
        list.add(new User(22));
        list.add(new User(33));

        return list;
    }

    private boolean sortUserList(boolean aToz){
        if(aToz){
            Arrays.sort(new ArrayList[]{this.userDataList});
        }else{
            Arrays.sort(new ArrayList[]{this.userDataList});
        }

        return true;
    }

    private String getTotalUserText(int userCount){
        return String.format("Total User Count:\n%d", userCount);
    }

    //click listener for userList
    private class ListClickListener implements RVClickListener{

        public void onItemClick(View view, int position){
            //do somethin
        }
    }

    //Menu implementation provided by: https://developer.android.com/guide/topics/ui/menus.html

    //creates the hamburger menu when pressed
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_list_menu , menu);
        return true;
    }

    //the menu options
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort:
                //Nothing goes here (I think?)
                return true;
            case R.id.sort_a2z:
                //To be implemented
                return true;
            case R.id.sort_z2a:
                //To be implemented
                return true;
            case R.id.delete:
                //To be implemented
                return true;
            default:
                return super.onOptionsItemSelected(item);
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