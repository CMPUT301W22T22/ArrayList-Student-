package com.arrayliststudent.qrhunt;

import java.util.ArrayList;

public class ScannableCode {

    String codeName;
    int codeScore;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    ScannableCode(String codeName, int codeScore){
        this.codeName = codeName;
        this.codeScore = codeScore;
    }

    ScannableCode() {}

    public String getCodeName() {
        return codeName;
    }

    public int getCodeScore() {
        return codeScore;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setCodeScore(int codeScore) {
        this.codeScore = codeScore;
    }


    /**
     * Adds comment to this particular code
     * @param comment
     */
    public void addComment(Comment comment){
        comments.add(comment);
    }

    /**
     * Retrieve arraylist of comments associated with ("on") this code
     * @return comments, an ArrayList of Comments
     */
    public ArrayList<Comment> getComments(){
        return comments;
    }
}
