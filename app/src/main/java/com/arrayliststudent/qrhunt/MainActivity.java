package com.arrayliststudent.qrhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.Authenticator;
import java.util.Observable;
import java.util.Observer;

/**
 * The MainActivity is the entry point of the application. When the MainPresenter is
 * constructed, the user data is fetched from the database. After a two second delay, a log in
 * is attempted by checking if the users Android ID exists in the user data. If it does, the
 * ConsoleActivity is started. If it doesn't, the user must input their user name and click
 * the register button. The MainPresenter then creates a new user with Anrdroid ID and user
 * name and starts the ConsoleActivity
 */
public class MainActivity extends AppCompatActivity {

    MAuthenticator auth;
    MainPresenter presenter;
    EditText nameEditTxt;
    Button confirmBtn;

    /**
     * Click listener for confirm button of new user registration. The user name is taken
     * from the nameEditText and the MainPresenter is used to create a new user. The
     * Console Activity is then started.
     * @param v
     * View object that was clicked, in this case confirmBtn.
     */
    private View.OnClickListener confirmBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = nameEditTxt.getText().toString();
            if(!name.isEmpty()){
                presenter.newUser(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID), name);
                Intent intent = new Intent(getApplicationContext(), ConsoleActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Name can't be empty",Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    /**
     * The onCreate() method instantiates the MAuthenticator class, constructs the MainPresenter,
     *  and sets the click listeners for all views for this Activity. A delayed function is
     *  posted to give the MainPresenter constructor time to finish the task which fetches user
     *  data. After 2 seconds, the MAuthenticator is used to attempt log in. If the user is
     *  already registered, the ConsoleActivity will start immediately.
     * @param savedInstanceState
     * Bundle saved from previous session.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = MAuthenticator.getInstance();
        presenter = new MainPresenter();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean login = auth.login(Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
                if(login) {
                    Intent intent = new Intent(getApplicationContext(), ConsoleActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
        nameEditTxt = findViewById(R.id.main_edit_username);
        confirmBtn = findViewById(R.id.main_btn_confirm);
        confirmBtn.setOnClickListener(confirmBtnClickListener);
    }
}