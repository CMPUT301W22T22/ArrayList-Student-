package com.arrayliststudent.qrhunt;

import java.util.ArrayList;

public class User {

    private String userId;
    private String name;

    ArrayList<ScannableCode> userCodeList;



    public User(String androidId, String name) {
        this.userId = androidId;
        this.name = name;
        this.userCodeList = new ArrayList<>();
    }

    public ArrayList<ScannableCode> getUserCodeList() {
        return userCodeList;
    }

    // equals() and hashCode implementations are necessary for use by Hash Map
    // To compare if two Users are equal, use the String equals() method to compare the Song.title String
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof User) {
            User s = (User)obj;
            result = this.userId.equals(s.userId);
        }
        return result;
    }

    // Use the Integer hashCode() from User.userID
    @Override
    public int hashCode() {
        return this.userId.hashCode();
    }

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (ScannableCode s : userCodeList) {
            totalScore += s.getCodeScore();
        }
        return totalScore;
    }

    public int getNumCodes() {
        return userCodeList.size();
    }

    public String getUserId() {
        return userId;
    }
}