package com.arrayliststudent.qrhunt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class represents a User object including their user name, user id, and list of previously
 * scanned ScannableCodes.
 */
public class User {

    private String userId;
    private String name;
    private String contactInfo = "None";
    private int totalScore;
    private int numCodes;

    private List<Map> userCodeList = new LinkedList<>();


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


    public List<Map> getUserCodeList() {
        return userCodeList;
    }

    // equals() and hashCode implementations are necessary for use by Hash Map
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof User) {
            User s = (User) obj;
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
     *
     * @return name
     * String representing the user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the total score of the user. Computes the total score by summing the score of
     * every ScannableCode in the userCodeList.
     *
     * @return totalScore
     * Integer representing the total score of the user.
     */
    public int getTotalScore() {

        return totalScore;
    }

    /**
     * Getter for the number of codes owned by the user.
     *
     * @return Integer for the size of the userCodeList.
     */
    public int getNumCodes() {
        return numCodes;
    }

    /**
     * Getter for the userId
     *
     * @return userId
     * String representing the unique android id of the user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Getter for the ContactInfo
     *
     * @return contactInfo
     * String representing the contactInfo of the user.
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Setter for the userName
     *
     * @param name String representing the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the userName
     *
     * @param contactInfo String representing the ContactInfo of the user.
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setCodeList(List<Map> userCodeList) {
        this.userCodeList = userCodeList;
    }

    public void setId(String value) {
        this.userId = value;
    }

    public void addToScore(int codeScore) {
        totalScore += codeScore;
    }

    public void setTotalScore(int initialScore) {
        this.totalScore = initialScore;
    }

    public void setNumCodes(int numCodes) {
        this.numCodes = numCodes;
    }

    public void addToNumCodes() {
        numCodes += 1;
    }

    public void addToNumCodes(int i) {
        numCodes += i;
    }


    public void addCode(ScannableCode codeData) {
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("codeName", codeData.getCodeName());
        codeMap.put("id", codeData.getId());
        userCodeList.add(codeMap);
    }
}