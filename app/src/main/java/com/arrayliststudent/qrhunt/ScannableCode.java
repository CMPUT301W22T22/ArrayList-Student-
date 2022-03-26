package com.arrayliststudent.qrhunt;

import java.util.ArrayList;

/**
 * This class represents a ScannableCode object including the codeName and codeScore. The next
 * release should also contain an optional image of the object scanned, and an optional
 * geolocation.
 */
public class ScannableCode {

    String codeName;
    int codeScore;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    ScannableCode(String codeName, int codeScore){
        this.codeName = codeName;
        this.codeScore = codeScore;
    }

    ScannableCode() {}

    @Override
    public int hashCode() {
        String hashString = codeName + Integer.toString(codeScore);
        return hashString.hashCode();
    }

    /**
     * Getter for the code name.
     * @return codeName
     * String representing the name of the ScannableCode.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Getter for the code score.
     * @return codeScore
     * String representing the score of the ScannableCode.
     */
    public int getCodeScore() {
        return codeScore;
    }

    /**
     * Setter for code name
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Setter for code score.
     */
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
