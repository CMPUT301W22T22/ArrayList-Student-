package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

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


}