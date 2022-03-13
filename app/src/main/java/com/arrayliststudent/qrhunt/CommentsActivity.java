package com.arrayliststudent.qrhunt;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    ListView commentView;
    ArrayList<Comment> comments;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);

        commentView =findViewById(R.id.comment_list);
        //get list of comments

        //create adapter
        commentAdapter = new CommentAdapter(this, comments);
        //tie view to adapter
        commentView.setAdapter(commentAdapter);
        //attach list to view
        commentView = findViewById(R.id.comment_list);
        //display list of comments
    }

}
