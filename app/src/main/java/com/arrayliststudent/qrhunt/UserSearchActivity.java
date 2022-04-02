package com.arrayliststudent.qrhunt;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class UserSearchActivity extends AppCompatActivity implements Observer {

    RecyclerView userSearchList;

    UserSearchPresenter presenter;
    UserSearchAdapter userSearchAdapter;

    //Custom clickListener for UserSearchList
    private class UserClickListener implements RVClickListener{

        @Override
        public void onItemClick(View itemView, int position){
            Toast.makeText(getApplicationContext(), "Opening User Profile", Toast.LENGTH_SHORT).show();

            //open user profile - send user ID to UserProfileActivity with an intent
            //not sure how this will work with search though
            Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
            intent.putExtra("idFromSearch", getUserNames().get(position));
            startActivity(intent);

        }
    }

    /**
     * Get all usernames in database... I think this is also done in UserSearchAdapter but whatever
     * @return Arraylist of usernames
     * @see UserSearchAdapter
     */
    private ArrayList<String> getUserNames(){
        UserDataModel model = UserDataModel.getInstance();
        model.fetchData();
        ArrayList<User> localUserList = (ArrayList<User>) model.getUsers();
        ArrayList<String> localUserNames = new ArrayList<>();
        for(User user: localUserList){
            localUserNames.add(user.getName());
        }
        return localUserNames;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        userSearchList = findViewById(R.id.usersearch_list_view);
        userSearchList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        userSearchAdapter = new UserSearchAdapter(new UserClickListener());
        userSearchList.setAdapter(userSearchAdapter);

        //presenter stuff
        presenter = new UserSearchPresenter();
        presenter.setUpObserver(this);
        presenter.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Start a search here...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                userSearchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void update(Observable observable, Object o) {
        userSearchAdapter.updateData();
        System.out.println("update method called");
    }
}
