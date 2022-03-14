package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class UserProfileActivity extends AppCompatActivity implements RemoveProfileFragment.OnFragmentInteractionListener, EditProfileFragment.OnFragmentInteractionListener , Observer {

    private User user;
    private TextView userName;
    private TextView contactInfo;
    private TextView score;
    private UserProfilePresenter userProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfilePresenter = new UserProfilePresenter();
        Intent intent = getIntent();
        setContentView(R.layout.activity_user_profile);
        UserDataModel model = UserDataModel.getInstance();
        //model.fetchData();
        HashMap<String, User> userHashMap = model.getUserList();
        //String android_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                //Settings.Secure.ANDROID_ID);
        user = userHashMap.get(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        userName = findViewById(R.id.UserName);
        contactInfo = findViewById(R.id.ContactInfo);
        score = findViewById(R.id.ContactInfo);
        userName.setText(user.getName());
        contactInfo.setText(user.getContactInfo());
        score.setText(String.valueOf(user.getTotalScore()));
    }

    public void editButton(View view){
        new EditProfileFragment(user).show(getSupportFragmentManager(),"EDIT");
    }

    public void CodeList(View view){

    }

    public void RemoveProfile(View view){
        new RemoveProfileFragment().show(getSupportFragmentManager(),"Try_Remove");
    }

    @Override
    public void onRemoveOKPressed() {

    }

    @Override
    public void onEditOKPressed(User user) {
        this.user = user;
        userName.setText(user.getName());
        contactInfo.setText(user.getTotalScore());
        userProfilePresenter.editUser(user);
    }

    @Override
    public void update(Observable observable, Object o) {
        UserDataModel model = UserDataModel.getInstance();
        User currentUser = model.getCurrentUser();
        userName.setText(currentUser.getName());
        score.setText(currentUser.getTotalScore());
    }
}