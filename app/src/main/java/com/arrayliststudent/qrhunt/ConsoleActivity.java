package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The ConsoleActivity is the main control panel for the application. From here the user can choose
 * to scan a new code, view their existing codes, see the highest ranked players, search for users
 * or nearby codes, open their user profile, or open the map. The user name of the current user
 * along with their total score and number of codes is also displayed at the top of the screen.
 */
public class ConsoleActivity extends AppCompatActivity implements Observer {

    ConsolePresenter presenter;
    TextView userTextView;
    TextView scoreTextView;
    TextView numCodesTextView;
    TextView viewCodesText;
    ImageView mapImageView;
    ImageView userImageView;
    ImageView searchImageView;
    ImageView ranksImageView;
    ImageView QRImageView;
    ImageView CameraImageView;
    CardView scanCodeView;

    Button testButton;

    /**
     * Click listener for QR code list button. This should open the list of QR codes that the
     * user has previously obtained. In this release, the activity showing the information of
     * a single QR code is started.
     * @param v
     * View object that was clicked, in this case the QR Code ImageView.
     */
    private View.OnClickListener onQRClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),CodeListActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Click listener for the Camera button. This should opens the QR scanner so the user can
     * take record a new QR code.
     * @param v
     * View object that was clicked, in this case the Camera ImageView.
     */
    private View.OnClickListener onCameraClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ScanCodeActivity.class);
        startActivity(intent);
        }
    };

    /**
     * Click listener for the Camera button. This opens the Map so the user can
     * view their location on the map and nearby QR codes.
     * @param v
     * View object that was clicked, in this case the Map ImageView.
     */
    private View.OnClickListener onMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Click listener for the User Profile button. This opens the User Profile for the current
     * user.
     * @param v
     * View object that was clicked, in this case the User Profile ImageView.
     */
    private View.OnClickListener onUserClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Click listener for the Search button. This opens the SearchActivity. To be completed
     * on next release.
     * @param v
     * View object that was clicked, in this case the Search ImageView.
     */
    private View.OnClickListener onSearchClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),UserSearchActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Click listener for the Search button. This opens the HighScoreListActivity which displays
     * the ranks and high scores of users. To be completed on next release.
     * @param v
     * View object that was clicked, in this case the Rank ImageView.
     */
    private View.OnClickListener onRankClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),HighScoreListActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onTestBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Random rd = new Random();
            byte[] arr = new byte[40];
            rd.nextBytes(arr);

            String hash = "";

            for(int i = 0; i < arr.length; i++){
                hash = hash + arr[i];
            }

            ScoreCalculator calc = new ScoreCalculator();
            int score = calc.getScore(hash);

            ScannableCode code = new ScannableCode("test name", score, hash);
            code.setLocation(getApplicationContext());
            System.out.println(code.getLocation());
            UserDataModel model = UserDataModel.getInstance();
            model.addCode(code);

        }
    };

    /**
     * The update method is called from the Observable class UserDataModel upon notifyObservers().
     * The intended purpose is to refresh any views with data from the UserDataModel.
     * @param o
     * The Observable class which called update(), which is the UserDataModel.
     * @param arg
     * Any object passed from the Observable, not used in this case.
     */
    @Override
    public void update(Observable o, Object arg) {
        UserDataModel model = UserDataModel.getInstance();
        User currentUser = model.getCurrentUser();
        userTextView.setText(currentUser.getName());
        scoreTextView.setText(String.valueOf(currentUser.getTotalScore()));
        numCodesTextView.setText(String.valueOf(currentUser.getNumCodes()));
    }

    /**
     * The onCreate() method constructs the ConsolePresenter, and sets the click listeners for all
     *  views for this Activity.
     * @param savedInstanceState
     * Bundle saved from previous session.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        userTextView = findViewById(R.id.console_text_user);
        scoreTextView = findViewById(R.id.console_text_score);
        numCodesTextView = findViewById(R.id.console_text_numcodes);
        presenter = new ConsolePresenter();
        presenter.setUpObserver(this);
        presenter.refresh();
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
        scanCodeView = findViewById(R.id.console_card_scancode);
        scanCodeView.setOnClickListener(onCameraClicked);
        viewCodesText = findViewById(R.id.console_txt_viewcodes);
        viewCodesText.setOnClickListener(onQRClicked);

        testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(onTestBtnClicked);

        // Initialize addCityButton
        final FloatingActionButton ownerButton = findViewById(R.id.toggle_owner_button);
        ownerButton.setOnClickListener((v)-> presenter.toggleOwner());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeObserver(this);
    }
}
