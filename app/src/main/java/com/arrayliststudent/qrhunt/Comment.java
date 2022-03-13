package com.arrayliststudent.qrhunt;

/**
 * @author Kieran
 *
 * Comment class used to store comments and their author. Also has getters.
 */
public class Comment {

    private String author;
    private String body;

    public Comment(String author, String content) {
        this.author = author;
        this.body = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }
}
