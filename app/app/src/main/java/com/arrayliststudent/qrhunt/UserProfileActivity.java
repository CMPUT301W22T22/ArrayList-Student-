package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity implements RemoveProfileFragment.OnFragmentInteractionListener, EditProfileFragment.OnFragmentInteractionListener{

    private UserProfile userProfile;
    private TextView userName;
    private TextView contactInfo;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.UserName);
        contactInfo = findViewById(R.id.ContactInfo);
        score = findViewById(R.id.ContactInfo);
        userName.setText(userProfile.getUserName());
        contactInfo.setText(String.valueOf(userProfile.getContactInfo()));
        score.setText(String.valueOf(userProfile.getScore()));
    }

    public void editButton(View view){
        new EditProfileFragment(userProfile).show(getSupportFragmentManager(),"EDIT");
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
    public void onEditOKPressed(UserProfile userProfile) {
        this.userProfile = userProfile;
        userName.setText(userProfile.getUserName());
        contactInfo.setText(String.valueOf(userProfile.getContactInfo()));
    }
}