package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.Authenticator;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    MAuthenticator auth;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        if(auth.login(Settings.Secure.ANDROID_ID)) {
        }
    }
}