package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Observable;
import java.util.Observer;

public class ConsoleActivity extends AppCompatActivity implements Observer {

    ConsolePresenter presenter;
    CustomAdapter userTextAdapter;
    TextView userTextView;
    TextView scoreTextView;
    TextView numCodesTextView;

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        presenter = new ConsolePresenter();
        presenter.setUpObserver(this);
        userTextView = findViewById(R.id.console_text_user);
        scoreTextView = findViewById(R.id.console_text_score);
        numCodesTextView = findViewById(R.id.console_text_numcodes);
    }
}
