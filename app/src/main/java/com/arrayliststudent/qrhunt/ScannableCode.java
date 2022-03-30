package com.arrayliststudent.qrhunt;

import android.content.Context;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * This class represents a ScannableCode object including the codeName and codeScore. The next
 * release should also contain an optional image of the object scanned, and an optional
 * geolocation.
 */
public class ScannableCode {

    String codeName;
    String id;
    int codeScore;
    double[] location;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    ScannableCode(String codeName, int codeScore){
        this.codeName = codeName;
        this.codeScore = codeScore;
        try
        {
            this.id = toHexString(getSHA(codeName));
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }

    }

    ScannableCode() {}

    @Override
    public int hashCode() {
        String hashString = codeName + Integer.toString(codeScore);
        return hashString.hashCode();
    }

    // https://www.geeksforgeeks.org/sha-256-hash-in-java/
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
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

    public String getId() {
        return this.id;
    }

    public void setLocation(Context context) {
        GPSLocation gpsLocation = new GPSLocation(context);
        this.location = gpsLocation.getDeviceLocation();
    }
}
