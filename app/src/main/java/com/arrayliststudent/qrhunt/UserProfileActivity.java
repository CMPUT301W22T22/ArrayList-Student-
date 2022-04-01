/*
* This Activity is used to show users their data,
* and allow users to modify and delete their data.
* At the same time, users can jump to other Activity to view their QRCode.
* */

package com.arrayliststudent.qrhunt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class UserProfileActivity extends AppCompatActivity implements RemoveProfileFragment.OnFragmentInteractionListener,
        EditProfileFragment.OnFragmentInteractionListener , Observer {

    private User user;
    private TextView userName;
    private TextView contactInfo;
    private TextView score;
    private UserProfilePresenter userProfilePresenter;
    private FirebaseFirestore db;

    /**
     * The onCreate() method instantiates the UserProfileActivity class
     * Initialize user's data and read user's data from firebase
     * When the data in firebase changes, the user's data also changes synchronously
     * @param savedInstanceState
     * Bundle saved from previous session.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfilePresenter = new UserProfilePresenter();
        Intent intent = getIntent();
        setContentView(R.layout.activity_user_profile);
        String android_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //user = firebaseData.getUser(android_ID);
        //user =  userProfilePresenter.getUsers().get(android_ID);
        user = new User(android_ID,"name");
        userName = findViewById(R.id.UserName);
        contactInfo = findViewById(R.id.ContactInfo);
        score = findViewById(R.id.UserScore);
        userName.setText(user.getName());
        //contactInfo.setText(user.getContactInfo());
        //score.setText(user.getTotalScore());

        db = FirebaseFirestore.getInstance();
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
                    String name = new String();
                    String contactInfo = new String();
                    List<Map> userCodeList = null;
                    Map<String,Object> user = doc.getData();
                    User addedUser;
                    boolean success = false;
                    for (Map.Entry<String, Object> pair : user.entrySet()) {

                        String key = pair.getKey();

                        if (pair.getKey().equals("userId")) {
                            System.out.println((String) pair.getValue());
                            userId = (String) pair.getValue();
                            success = true;
                        }
                        if (pair.getKey().equals("name")) {
                            System.out.println((String) pair.getValue());
                            name = (String) pair.getValue();
                            success = true;
                        }
                        if (pair.getKey().equals("contactInfo")){
                            System.out.println((String) pair.getValue());
                            contactInfo = (String) pair.getValue();
                            success = true;
                        }
                        if (pair.getKey().equals("userCodeList")) {
                            userCodeList = (List) pair.getValue();
                        }
                    }
                    if(success) {
                        addedUser = new User(userId, name, contactInfo);
                        if(userCodeList != null) {
                            addedUser.setCodeList(userCodeList);
                        }
                        userDataList.put(userId, addedUser);
                        Log.d(TAG, "User " + userId + " downloaded");
                    }
                }

                if (!userDataList.isEmpty()){
                    user = userDataList.get(android_ID);
                    if(user!=null){
                        userName.setText(user.getName());
                        contactInfo.setText(user.getContactInfo());
                        score.setText(String.valueOf(user.getTotalScore()));
                    }
                }
            }

        });

    }

    /**
     * Onclick for EditButton
     * @param view
     * View that button need.
     */
    public void editButton(View view){
        new EditProfileFragment(user).show(getSupportFragmentManager(),"EDIT");
    }

    /**
     * Onclick for CodeListButton
     * @param view
     * View that button need.
     */
    public void CodeList(View view){

        Intent intent = new Intent(this,CodeListActivity.class);
        startActivity(intent);
    }

    /**
     * Onclick for RemoveButton
     * @param view
     * View that button need.
     */
    public void RemoveProfile(View view){
        new RemoveProfileFragment().show(getSupportFragmentManager(),"Try_Remove");
    }


    /**
     * Onclick for RemoveOK
     * Remove User Profile
     */
    @Override
    public void onRemoveOKPressed() {

        userProfilePresenter.removeUser(user);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }


    /**
     * Onclick for EditOKButton
     * @param user
     * user that changed.
     */
    @Override
    public void onEditOKPressed(User user) {
        //this.user = user;
        userName.setText(user.getName());
        contactInfo.setText(String.valueOf(user.getTotalScore()));
        userProfilePresenter.editUser(user,this.user);
        this.user = user;
    }

    /**
     * Onclick for EditButton
     * @param observable, o
     * update data
     */
    @Override
    public void update(Observable observable, Object o) {
        UserDataModel model = UserDataModel.getInstance();
        User currentUser = model.getCurrentUser();
        userName.setText(currentUser.getName());
        score.setText(String.valueOf(user.getTotalScore()));
    }
}