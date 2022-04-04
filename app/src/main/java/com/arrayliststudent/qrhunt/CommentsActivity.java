package com.arrayliststudent.qrhunt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.Status;

/**
 * @author Kieran
 *
 * Comment activity class. Retrives comments for a given QR code and displays them, along with
 * their author.
 *
 * Launches fragment for adding comment.
 *
 */
public class CommentsActivity extends AppCompatActivity implements AddCommentFragment.OnFragmentInteractionListener{

    ListView commentView;
    ArrayList<Comment> comments;
    CommentAdapter commentAdapter;
    ScannableCode scannableCode;
    String codeId;
    Boolean stat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);
        Intent intent = getIntent();
        scannableCode = (ScannableCode) intent.getSerializableExtra("code");
        codeId = scannableCode.getId();
        //UpdateCodeInfo();
        CommentPresenter presenter = new CommentPresenter(codeId);
        presenter.setListener(new CommentPresenter.OnGetDataListener() {
            @Override
            public void getNewCode(ScannableCode code) {
                if(stat){
                    scannableCode = code;
                    comments = scannableCode.getComments();
                    commentAdapter = new CommentAdapter(getApplicationContext(),comments);
                    commentView.setAdapter(commentAdapter);
                    stat = false;
                }
            }
        });

        commentView =findViewById(R.id.comment_list);
        //get list of comments

        //placeholder comments variable until database is hooked up
        comments = new ArrayList<Comment>();
        //create adapter

        presenter.UpdateCodeInfo();
        if(scannableCode!=null){
            comments = scannableCode.getComments();
        }

        commentAdapter = new CommentAdapter(this, comments);

        //tie view to adapter

        commentView.setAdapter(commentAdapter);

        //attach list to view

        //display list of comments

        final Button addCommentButton = findViewById(R.id.add_comment_butt);
        addCommentButton.setOnClickListener((v) ->
                new AddCommentFragment().show(getSupportFragmentManager(), "ADD_COMMENT"));
    }



    public void UploadCodeInfo(ScannableCode code){
        FirebaseData firebaseData = new FirebaseData();
        firebaseData.addCode(code);
    }

    @Override
    public void onCommentOkPressed(String comment) {
        //add to list of comments (need author)
        UserDataModel model = UserDataModel.getInstance();
        User user = model.getCurrentUser();
        scannableCode.addComment(new Comment(user.getName(), comment));
        UploadCodeInfo(scannableCode);
        //commentAdapter.add(new Comment(user.getName(), comment));
        commentAdapter.notifyDataSetChanged();
    }

}
