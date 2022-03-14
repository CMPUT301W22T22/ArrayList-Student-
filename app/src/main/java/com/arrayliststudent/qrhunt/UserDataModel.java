package com.arrayliststudent.qrhunt;


import android.provider.Settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

public class UserDataModel extends Observable {

    FirebaseData database;
    HashMap<String, User> userList;

    private UserDataModel() {
        database = new FirebaseData();
    }

    private static final UserDataModel userDataModel = new UserDataModel();

    private String userID;

    public static UserDataModel getInstance() {
        return userDataModel;
    }

    public ArrayList<ScannableCode> getLocalData() {
        return userList.get(userID).getUserCodeList();
    }

    public void addCode(ScannableCode code) {
        User user = userList.get(this.userID);
        user.getUserCodeList().add(code);
        database.addUserData(user);
        setChanged();
        notifyObservers();
    }


    public User getCurrentUser() {
        return userList.get(userID);
    }

    public Collection<User> getUsers() {
        return userList.values();
    }

    public boolean checkUserID(String userID) {
        System.out.println("stuff happened");

        userList = database.fetchUserData();

        if (userList.containsKey(userID)) {
            System.out.println("user id " + userID + " found");
            this.userID = userID;
            return true;
        } else {
            System.out.println("user id " + userID + " not found");
            return false;
        }
    }

    public HashMap<String, User> getUserList() {
        return userList;
    }

    public void editUser(User user){
        database.removerUserData(getCurrentUser());
        database.addUserData(user);
    }

    public void newUser(String userID, String userName) {
        database.addUserData(new User(userID, userName));

    }

    public void fetchData() {
        userList = database.fetchUserData();
    }
}
