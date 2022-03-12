package com.arrayliststudent.qrhunt;

import java.util.ArrayList;

public class ScannableCode {

    String codeName;
    int codeScore;
    private ArrayList<Comment> comments;

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

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public ArrayList<Comment> getComments(){
        return comments;
    }
}
