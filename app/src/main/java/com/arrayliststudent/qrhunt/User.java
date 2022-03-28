package com.arrayliststudent.qrhunt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a User object including their user name, user id, and list of previously
 * scanned ScannableCodes.
 */
public class User {

    private String userId;
    private String name;
    private String contactInfo = "None";

    private List<String> userCodeList = new LinkedList<String>();


    public User(String androidId, String name) {
        this.userId = androidId;
        this.name = name;
    }

    public User(String androidId, String name, String contactInfo) {
        this.userId = androidId;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public User() {

    }

    public List<String> getUserCodeList() {
        return userCodeList;
    }

    // equals() and hashCode implementations are necessary for use by Hash Map
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
//        for (String s : userCodeList) {
//            if ()
//            totalScore += Integer.valueOf(s);
//        }
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

    /**
     * Getter for the ContactInfo
     * @return contactInfo
     * String representing the contactInfo of the user.
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Setter for the userName
     * @param name
     * String representing the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the userName
     * @param contactInfo
     * String representing the ContactInfo of the user.
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setCodeList(List<String> userCodeList) {
        this.userCodeList = userCodeList;
    }

    public void setId(String value) {
        this.userId = value;
    }
}
