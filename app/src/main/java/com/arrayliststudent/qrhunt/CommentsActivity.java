package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author Kieran
 *
 * Comment activity class. Currently just does local, unsaved adding/viewing of comments for
 * demonstration purposes... does not seem to pass comment from fragment to activity 3/13
 *
 * Launches fragment for adding comment.
 *
 * Known issue 3/13: The comment seems to be passed from the fragment to the activity but is not
 * displayed. Liekly will be resolved upon database integration.
 */
public class CommentsActivity extends AppCompatActivity implements AddCommentFragment.OnFragmentInteractionListener{

    ListView commentView;
    ArrayList<Comment> comments;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);

        commentView =findViewById(R.id.comment_list);
        //get list of comments

        //placeholder comments variable until database is hooked up
        comments = new ArrayList<Comment>();
        //create adapter

        commentAdapter = new CommentAdapter(this, comments);

        //tie view to adapter

        commentView.setAdapter(commentAdapter);

        //attach list to view

        //display list of comments

        final Button addCommentButton = findViewById(R.id.add_comment_butt);
        addCommentButton.setOnClickListener((v) ->
                new AddCommentFragment().show(getSupportFragmentManager(), "ADD_COMMENT"));
    }

    @Override
    public void onCommentOkPressed(String comment) {
        //add to list of comments (need author)
        commentAdapter.add(new Comment("author", comment));

    }

}
