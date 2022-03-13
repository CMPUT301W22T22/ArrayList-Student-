package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Observable;
import java.util.Observer;

public class ConsoleActivity extends AppCompatActivity implements Observer {

    ConsolePresenter presenter;
    CustomAdapter userTextAdapter;
    TextView userTextView;
    TextView scoreTextView;
    TextView numCodesTextView;
    ImageView mapImageView;
    ImageView userImageView;
    ImageView searchImageView;
    ImageView ranksImageView;
    ImageView QRImageView;
    ImageView CameraImageView;


    private View.OnClickListener onQRClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),QRCodeActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onCameraClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
//            startActivity(intent);
        }
    };

    private View.OnClickListener onMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onUserClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onSearchClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
//            startActivity(intent);
        }
    };

    private View.OnClickListener onRankClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),HighScoreListActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        UserDataModel model = UserDataModel.getInstance();
        //User currentUser = model.getCurrentUser();
        //userTextView.setText(currentUser.getName());
       // scoreTextView.setText(currentUser.getTotalScore());
        //numCodesTextView.setText(currentUser.getNumCodes());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        userTextView = findViewById(R.id.console_text_user);
        scoreTextView = findViewById(R.id.console_text_score);
        numCodesTextView = findViewById(R.id.console_text_numcodes);
        presenter = new ConsolePresenter();
        presenter.setUpObserver(this);

        /*
        mapImageView = findViewById(R.id.console_img_map);
        mapImageView.setOnClickListener(onMapClicked);
        userImageView = findViewById(R.id.console_img_user);
        userImageView.setOnClickListener(onUserClicked);
        searchImageView = findViewById(R.id.console_img_search);
        searchImageView.setOnClickListener(onSearchClicked);
        ranksImageView = findViewById(R.id.console_img_rank);
        ranksImageView.setOnClickListener(onRankClicked);
        QRImageView = findViewById(R.id.console_img_qr);
        QRImageView.setOnClickListener(onQRClicked);
        CameraImageView = findViewById(R.id.console_img_camera);
        CameraImageView.setOnClickListener(onCameraClicked);

         */

    }


}
