package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

    ImageView commentsImageView;

    private View.OnClickListener onCommentsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(getApplicationContext(),CommentsActivity.class);
            //startActivity(intent);
        }
    };

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        nameTextView = findViewById(R.id.qr_text_name);
        scoreTextView = findViewById(R.id.qr_text_score);
        geolocTextView = findViewById(R.id.qr_text_geoloc);
        presenter = new QRPresenter();
        presenter.setUpObserver(this);

        commentsImageView = findViewById(R.id.qr_img_comments);
        commentsImageView.setOnClickListener(onCommentsClicked);

    }

}
