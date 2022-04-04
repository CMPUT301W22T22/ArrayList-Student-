package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

/**
 * The CodeListActivity displays a list of ScannableCodes owned by the current user
 */
public class CodeListActivity extends AppCompatActivity implements Observer {

    RecyclerView codeList;
    CodeListPresenter presenter;
    CodeListAdapter recyclerAdapter;

    /**
     * Click Listener for Recycler View
     */
    private class ListClickListener implements RVClickListener {
        @Override
        public void onItemClick(View itemView, int position) {
            UserDataModel model = UserDataModel.getInstance();
            model.setCodePosition(position);
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

    /**
     * The onCreate() method instantiates the CodeListPresenter class and sets the click listeners for
     *  all views for this Activity.
     * @param savedInstanceState
     * Bundle saved from previous session.
     */
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

    /**
     * The update method is called from the Observable class UserDataModel upon notifyObservers().
     * The intended purpose is to refresh any views with data from the UserDataModel.
     * @param observable
     * The Observable class which called update(), which is the UserDataModel.
     * @param o
     * Any object passed from the Observable, not used in this case.
     */
    @Override
    public void update(Observable observable, Object o) {
        recyclerAdapter.updateData();
    }
}
