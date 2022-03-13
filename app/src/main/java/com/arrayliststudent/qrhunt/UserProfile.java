package com.arrayliststudent.qrhunt;

public class UserProfile {
    private String userName = "No_User_Name";
    private int contactInfo = -1;
    private Boolean Admin = false;
    private int score = 0;


    public UserProfile(String userName, int contactInfo, Boolean Admin){
        this.Admin = Admin;
        this.userName = userName;
        this.contactInfo = contactInfo;
    }

    public UserProfile(){

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
