package com.arrayliststudent.qrhunt;

import java.util.ArrayList;
import java.util.Comparator;

public class User implements Comparable{

    private Integer userID;

    ArrayList<ScannableCode> userCodeList;

    public User(int id) {
        this.userID = id;
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
            result = this.userID.equals(s.userID);
        }
        return result;
    }

    public String getUserName(){
        return "moogly";
    }

    // Use the Integer hashCode() from User.userID
    @Override
    public int hashCode() {
        return this.userID.hashCode();
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
            String a = this.getUserName();
            String b = ((User) user).getUserName();
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
