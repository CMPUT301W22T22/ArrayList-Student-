package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity implements AddCommentFragment.OnFragmentInteractionListener{

    ListView commentView;
    ArrayList<Comment> comments;
    CommentAdapter commentAdapter;

    private View.OnClickListener onACButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //launch add comment fragment

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);

        commentView =findViewById(R.id.comment_list);
        //get list of comments

        //create adapter

        //commentAdapter = new CommentAdapter(this, comments);

        //tie view to adapter

        //commentView.setAdapter(commentAdapter);

        //attach list to view
        //display list of comments

        final Button addCommentButton = findViewById(R.id.add_comment_butt);
        addCommentButton.setOnClickListener((v) ->
                new AddCommentFragment().show(getSupportFragmentManager(), "ADD_COMMENT"));
    }

    @Override
    public void onCommentOkPressed(String comment) {
        //add to list of comments (need author)
    }
}
