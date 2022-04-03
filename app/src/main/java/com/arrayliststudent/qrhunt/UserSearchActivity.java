package com.arrayliststudent.qrhunt;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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

public class UserSearchActivity extends AppCompatActivity implements UserSearchFragment.OnFragmentInteractionListener {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    private ArrayList<String> getUserNames(){
        ArrayList<String> name = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: value) {
                    Map<String, Object> code = doc.getData();
                    for (Map.Entry<String, Object> pair : code.entrySet()) {
                        String key = pair.getKey();
                        if (pair.getKey().equals("name")) {
                            name.add((String) pair.getValue());
                            Log.d(TAG,"Added name " + (String) pair.getValue());
                        }
                    }
                }
            }
        });
        return name;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        List<String> nameList = getUserNames();
        System.out.println("%%%"+nameList);


        listView = findViewById(R.id.list_item);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //new UserSearchFragment(nameList.get(position)).show(getSupportFragmentManager(),"get user data");
                User user;
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final String TAG = "dunno what to put here";
                final CollectionReference collectionReference = db.collection("Users");
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                            FirebaseFirestoreException error) {
                        HashMap<String, User> userDataList = new HashMap<>();
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            String userId = new String();
                            Map<String,Object> user = doc.getData();
                            User user1 = new User();
                            boolean success = false;
                            for (Map.Entry<String, Object> pair : user.entrySet()) {

                                String key = pair.getKey();

                                if (pair.getKey().equals("userId")) {
                                    user1.setId((String) pair.getValue());
                                    userId = (String) pair.getValue();
                                }
                                if (pair.getKey().equals("name")) {
                                    user1.setName((String) pair.getValue());
                                }
                                if (pair.getKey().equals("contactInfo")){
                                    user1.setContactInfo((String) pair.getValue());
                                }
                                if (pair.getKey() == "userCodeList") {
                                    user1.setCodeList((List) pair.getValue());
                                }
                                if (pair.getKey().equals("numCodes")) {
                                    Integer numCodes;
                                    numCodes = ((Long) pair.getValue()).intValue();
                                    user1.setNumCodes(numCodes);
                                }
                                if (pair.getKey().equals("totalScore")) {
                                    Integer totalScore;
                                    totalScore = ((Long) pair.getValue()).intValue();
                                    user1.setTotalScore(totalScore);
                                }
                            }
                            if(true) {
                                userDataList.put(userId, user1);
                                Log.d(TAG, "User " + userId + " downloaded");
                            }
                        }

                        if (!userDataList.isEmpty()){
                            for(User user1 : userDataList.values()){
                                if(user1.getName().equals(nameList.get(position))){
                                    new UserSearchFragment(user1.getName(),user1.getTotalScore(),user1.getNumCodes()).show(getSupportFragmentManager(),"get user data");
                                }
                            }
                        }
                    }

                });
            }
        });
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

                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
