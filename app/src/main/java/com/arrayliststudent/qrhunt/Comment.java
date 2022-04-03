package com.arrayliststudent.qrhunt;

import java.io.Serializable;

/**
 * @author Kieran
 *
 * Comment class used to store comments and their author. Also has getters.
 */
public class Comment implements Serializable {

    private String author;
    private String body;

    /**
     * Comment constructor
     * @param author The author of the comment (i.e the user)
     * @param body The comment itself (the body of text that is the comment)
     */
    public Comment(String author, String body) {
        this.author = author;
        this.body = body;
    }



    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }
}
