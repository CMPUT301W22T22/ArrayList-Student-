package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * The QRCodeActivity displays the information for a given ScannableCode.
 */
public class QRCodeActivity extends AppCompatActivity implements Observer {
    QRPresenter presenter;
    CustomAdapter nameTextAdapter;
    CustomAdapter scoreTextAdapter;
    CustomAdapter geolocTextAdapter;

    TextView nameTextView;
    TextView scoreTextView;
    TextView geolocTextView;

    ImageView commentsImageView;

    /**
     * Click listener for comments button. This starts the CommentsActivity.
     * @param v
     * View object that was clicked, in this case the Comments ImageView.
     */
    private View.OnClickListener onCommentsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),CommentsActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * The onCreate() method instantiates the QRPresenter class and sets the click listeners for
     *  all views for this Activity.
     * @param savedInstanceState
     * Bundle saved from previous session.
     */
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
