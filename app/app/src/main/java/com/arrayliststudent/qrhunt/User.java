package com.arrayliststudent.qrhunt;

public class User {
    private String userName;
    private int contactInfo;
    private Boolean Admin;
    private int score;


    public User(String userName,int contactInfo,Boolean Admin){
        this.Admin = Admin;
        this.userName = userName;
        this.contactInfo = contactInfo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setAdmin(Boolean admin) {
        Admin = admin;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getContactInfo() {
        return contactInfo;
    }

    public Boolean getAdmin() {
        return Admin;
    }

    public int getScore() {
        return score;
    }
}
