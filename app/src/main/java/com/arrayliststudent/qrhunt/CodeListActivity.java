package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class CodeListActivity extends AppCompatActivity {
    RecyclerView codeList;
    TextView totalCodeScore;
    TextInputEditText searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);

        codeList = findViewById(R.id.code_list_view);
        totalCodeScore = findViewById(R.id.total_codescore_view);
        searchbar = findViewById(R.id.search_bar);
    }
}
