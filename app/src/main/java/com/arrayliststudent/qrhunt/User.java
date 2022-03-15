package com.arrayliststudent.qrhunt;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class represents a User object including their user name, user id, and list of previously
 * scanned ScannableCodes.
 */
public class User implements Comparable{

    private String userId;
    private String name;
    private String contactInfo = "None";

    ArrayList<ScannableCode> userCodeList;

    public User(String androidId, String name) {
        this.userId = androidId;
        this.name = name;
        this.userCodeList = new ArrayList<>();
    }

    public User(String androidId, String name, String contactInfo) {
        this.userId = androidId;
        this.name = name;
        this.contactInfo = contactInfo;
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

    /**
     * Getter for the user name.
     * @return name
     * String representing the user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the total score of the user. Computes the total score by summing the score of
     * every ScannableCode in the userCodeList.
     * @return totalScore
     * Integer representing the total score of the user.
     */
    public int getTotalScore() {
        int totalScore = 0;
        for (ScannableCode s : userCodeList) {
            totalScore += s.getCodeScore();
        }
        return totalScore;
    }

    /**
     * Getter for the number of codes owned by the user.
     * @return
     * Integer for the size of the userCodeList.
     */
    public int getNumCodes() {
        return userCodeList.size();
    }

    /**
     * Getter for the userId
     * @return userId
     * String representing the unique android id of the user.
     */
    public String getUserId() {
        return userId;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Follows standard alphabetical sorting with usernames
     * @param user
     * @return
     * An int representing the difference
     */
    @Override
    public int compareTo(Object user) {
        if(user instanceof User){
            String a = this.getName();
            String b = ((User) user).getName();
            return a.compareTo(b);
        }
        return 0;
    }

    public static Comparator<User> reverseAlphabeticalComparator
            = new Comparator<User>() {

        @Override
        public int compare(User a, User b) {
            return -(a.compareTo(b));
        }
    };
}
