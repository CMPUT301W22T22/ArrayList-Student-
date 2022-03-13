package com.arrayliststudent.qrhunt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

public class UserDataModel extends Observable {

    FirebaseData database;
    HashMap<Integer, User> userList;

    private UserDataModel() {
        userList = database.fetchUserData();
    }

    private static final UserDataModel userDataModel = new UserDataModel();

    private Integer userID;

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

    public void setUserID(Integer userID) {
        this.userID = userID;
        //userList = new HashMap<>();

        if (!userList.containsKey(userID)) {
            User newUser = new User(userID, "name");
            userList.put(userID, newUser);
            database.addUserData(newUser);
        }
    }

    public Collection<User> getUsers() {
        return userList.values();
    }
}
