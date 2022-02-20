package com.example.recyclerobserver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class UserDataModel extends Observable {

    HashMap<Integer, User> userList;

    private UserDataModel() {
        this.userList = new HashMap<>();
    }
    private static final UserDataModel userDataModel = new UserDataModel();
    public static UserDataModel getInstance() {
        return userDataModel;
    }


    private Integer userID;


    public ArrayList<ScannableCode> getLocalData() {
        return userList.get(userID).getUserCodeList();
    }

    public void addCode(ScannableCode code) {
        userList.get(this.userID).getUserCodeList().add(code);
        setChanged();
        notifyObservers();
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
        userList = new HashMap<>();
        if (!userList.containsKey(userID)) {
            userList.put(userID, new User(userID));
        }
    }
}
