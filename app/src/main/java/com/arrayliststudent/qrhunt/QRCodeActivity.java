package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The QRCodeActivity displays the information for a given ScannableCode.
 */
public class QRCodeActivity extends AppCompatActivity implements Observer {

    QRPresenter presenter;
    TextView nameTextView;
    TextView scoreTextView;
    TextView geolocTextView;
    ImageView qrPhotoImageView;
    ImageView commentsImageView;
    ImageView deleteImageView;
    ImageView usersImageView;

    ScannableCode code;

    /**
     * Click listener for comments button. This starts the CommentsActivity.
     * @param v
     * View object that was clicked, in this case the Comments ImageView.
     */
    private View.OnClickListener onCommentsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),CommentsActivity.class);
            intent.putExtra("code",code);
            startActivity(intent);
        }
    };

    /**
     * Click listener for delete button. This deletes the QRCode from the app and returns to
     * the previous activity.
     * @param v
     * View object that was clicked, in this case the Delete ImageView.
     */
    private View.OnClickListener onDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.deleteCode(code);
            finish();
        }
    };

    /**
     * Click listener for Users button. This starts the UserListActivity showing other Users
     * who have the same code.
     * @param v
     * View object that was clicked, in this case the Users ImageView.
     */
    private View.OnClickListener onUsersClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
            intent.putExtra("ID",code.getId());
            startActivity(intent);
        }
    };

    /**
     * This update method was not used since the data from the QRCodeActivity is set from the
     * ScannableCode passed to the Activity using an intent
     * @param o
     * The Observable class which called update(), which is the UserDataModel.
     * @param arg
     * Any object passed from the Observable, not used in this case.
     */
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


        qrPhotoImageView = findViewById(R.id.qr_img_photo);
        commentsImageView = findViewById(R.id.qr_img_comments);
        commentsImageView.setOnClickListener(onCommentsClicked);
        deleteImageView = findViewById(R.id.qr_img_delete);
        deleteImageView.setOnClickListener(onDeleteClicked);
        usersImageView = findViewById(R.id.qr_img_users);
        usersImageView.setOnClickListener(onUsersClicked);

        deleteImageView.setVisibility(View.INVISIBLE);

        MAuthenticator auth = MAuthenticator.getInstance();
        if(auth.checkPrivledge()) {
            deleteImageView.setVisibility(View.VISIBLE);
        }

        Bundle extras = getIntent().getExtras();

        // This block gets the ScannableCode to be used for this Activity that was passed
        // in the intent from the CodeListActivity
        if (extras != null ) {
            this.code = (ScannableCode) extras.getSerializable("code");
            nameTextView.setText("Name: " + code.getCodeName());
            scoreTextView.setText("Score: " + (String.valueOf(code.getCodeScore())));
            List<Double> loc = code.getLocation();
            String locString = loc.toString();
            geolocTextView.setText("Location: " + locString);

            File photo = code.getPhotoFile();
            if(photo.exists()){
                Bitmap bitmap =  BitmapFactory.decodeFile(photo.getAbsolutePath());
                qrPhotoImageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * The onPause() method saves the ScannableCode used for this activity so that it
     * will be avaiable when returning from the CommentsActivity
     */
    @Override
    public void onPause() {
        super.onPause();
        UserDataModel model = UserDataModel.getInstance();
        model.saveCode(code);
    }

    /**
     * The onPause() method restores the ScannableCode used for this activity so that it
     * will be avaiable when returning from the CommentsActivity
     */
    @Override
    public void onResume() {
        super.onResume();
        if(this.code == null) {
            UserDataModel model = UserDataModel.getInstance();
            code = model.getSavedCode();
            nameTextView.setText("Name: " + code.getCodeName());
            scoreTextView.setText("Score: " + (String.valueOf(code.getCodeScore())));
            List<Double> loc = code.getLocation();
            String locString = loc.toString();
            geolocTextView.setText("Location: " + locString);

            File photo = code.getPhotoFile();
            if(photo.exists()){
                Bitmap bitmap =  BitmapFactory.decodeFile(photo.getAbsolutePath());
                qrPhotoImageView.setImageBitmap(bitmap);
            }
        }

    }

}
