package com.example.recyclerobserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements AddCodeFragment.OnFragmentInteractionListener, Observer {
    MainPresenter presenter;
    CustomRecyclerAdapter recyclerAdapter;
    RecyclerView codeList;

    @Override
    public void onOkPressed(ScannableCode code) {
        presenter.addNewCode(code);
    }

    @Override
    public void update(Observable o, Object arg) {
        recyclerAdapter.updateData();
    }

    // ClickListener for RecyclerView
    private class ListClickListener implements RecyclerClickListener {
        @Override
        public void onItemClick(View itemView, int position) {
            Toast.makeText(getApplicationContext(), "Good click", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter();
        presenter.setUpUser(1);
        presenter.setUpObserver(this);

        codeList = findViewById(R.id.codelist_recycler);
        codeList.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new CustomRecyclerAdapter(new ListClickListener());
        codeList.setAdapter(recyclerAdapter);

        // Initialize addCityButton
        final FloatingActionButton addCityButton = findViewById(R.id.codelist_btn_add);
        addCityButton.setOnClickListener((v)-> new AddCodeFragment().show(getSupportFragmentManager(),"ADD_CITY"));
    }
}