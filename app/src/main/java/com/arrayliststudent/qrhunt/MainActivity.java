package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.Authenticator;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    MAuthenticator auth;
    MainPresenter presenter;
    Button confirmBtn;
    EditText nameEditTxt;


    private View.OnClickListener confirmBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = nameEditTxt.getText().toString();
            presenter.newUser(name, Settings.Secure.ANDROID_ID);
            Intent intent = new Intent(getApplicationContext(), ConsoleActivity.class);
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = MAuthenticator.getInstance();
        presenter = new MainPresenter();
        if(auth.login(Settings.Secure.ANDROID_ID)) {
            Intent intent = new Intent(this, ConsoleActivity.class);
            startActivity(intent);
        }
        nameEditTxt = findViewById(R.id.main_edit_username);
        confirmBtn = findViewById(R.id.main_btn_confirm);
        confirmBtn.setOnClickListener(confirmBtnClickListener);
    }


}