package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class CodeListActivity extends AppCompatActivity implements Observer {
    RecyclerView codeList;


    CodeListPresenter presenter;
    CodeListAdapter recyclerAdapter;

    // ClickListener for RecyclerView
    private class ListClickListener implements RVClickListener {
        @Override
        public void onItemClick(View itemView, int position) {
            UserDataModel model = UserDataModel.getInstance();
            ArrayList<ScannableCode> localDataset = model.getUserCodes();

            ScannableCode code = localDataset.get(position);

            ArrayList<Map> localCodeList = model.getUserCodeList();

            Map<String, String> dataMap = localCodeList.get(position);
            for (Map.Entry<String, String> pair : dataMap.entrySet()) {
                String key = pair.getKey();
                if (key.equals("codeName")) {
                    code.setCodeName(pair.getValue());
                }
            }


            Intent intent = new Intent(getApplicationContext(),QRCodeActivity.class);
            intent.putExtra("code", code);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_list);

        presenter = new CodeListPresenter();
        presenter.setUpObserver(this);

        codeList = findViewById(R.id.code_list_view);
        codeList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerAdapter = new CodeListAdapter(new ListClickListener());


        codeList.setAdapter(recyclerAdapter);

        presenter = new CodeListPresenter();
        presenter.setUpObserver(this);
        presenter.refresh();

    }

    @Override
    public void update(Observable observable, Object o) {
        recyclerAdapter.updateData();
    }
}
