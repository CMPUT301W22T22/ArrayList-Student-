package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CodeListActivity extends AppCompatActivity {
    private RecyclerView codeList;
    private TextInputEditText searchbar;
    private TextView totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);

        codeList = findViewById(R.id.code_list_view);
        searchbar = findViewById(R.id.search_bar);
        totalScore = findViewById(R.id.total_score_view);
    }
}
