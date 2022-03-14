package com.arrayliststudent.qrhunt;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * this test is for the comment class and also the comments in ScannableCode
 */
public class CommentTest {

    @Test
    void testCommentClass(){
        Comment comment = new Comment("Kieran", "Hello world");
        assertEquals("Kieran", comment.getAuthor());
        assertEquals("Hello world", comment.getBody());
    }

    @Test
    void testCodeComments(){
        ScannableCode code = new ScannableCode();
        Comment comment = new Comment("Kieran", "Hello world");

        code.addComment(comment);
        assertEquals(comment, code.getComments().get(0));
    }
}
