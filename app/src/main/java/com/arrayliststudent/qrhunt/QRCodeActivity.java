package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

public class QRCodeActivity extends AppCompatActivity implements Observer {
    QRPresenter presenter;
    CustomAdapter nameTextAdapter;
    CustomAdapter scoreTextAdapter;
    CustomAdapter geolocTextAdapter;

    TextView nameTextView;
    TextView scoreTextView;
    TextView geolocTextView;

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        presenter = new QRPresenter();
        presenter.setUpObserver(this);
        nameTextView = findViewById(R.id.qr_text_name);
        scoreTextView = findViewById(R.id.qr_text_score);
        geolocTextView = findViewById(R.id.qr_text_geoloc);
    }

}
