package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Observable;
import java.util.Observer;

public class CodeListActivity extends AppCompatActivity implements Observer {
    RecyclerView codeList;
    TextView totalCodeScore;
    TextInputEditText searchbar;

    //CodeListPresenter presenter;
    CodeListAdapter recyclerAdapter;

    // ClickListener for RecyclerView
    private class ListClickListener implements RVClickListener {
        @Override
        public void onItemClick(View itemView, int position) {
            Toast.makeText(getApplicationContext(), "Good click", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_list);

        //presenter = new CodeListPresenter();
        //presenter.setUpObserver(this);

        codeList = findViewById(R.id.code_list_view);
        codeList.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new CodeListAdapter(new ListClickListener());

        totalCodeScore = findViewById(R.id.total_codescore_view);
        searchbar = findViewById(R.id.search_bar);
        codeList.setAdapter(recyclerAdapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //presenter.refresh();
            }
        }, 1000);

    }

    @Override
    public void update(Observable observable, Object o) {
        recyclerAdapter.updateData();
        System.out.println("update method called");
    }
}
