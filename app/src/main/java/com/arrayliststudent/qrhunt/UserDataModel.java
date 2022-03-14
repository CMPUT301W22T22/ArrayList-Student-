package com.arrayliststudent.qrhunt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

/**
 * The UserDataModel is a singleton which is responsible for storing all data of the application.
 */
public class UserDataModel extends Observable {

    FirebaseData database;
    HashMap<String, User> userList;
    private String userID;

    /**
     * Singleton pattern for UserDataModel. Constructor initializes the database.
     */
    private UserDataModel() {
        database = new FirebaseData();
    }
    private static final UserDataModel userDataModel = new UserDataModel();
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

    /**
     * Getter for the current user
     * @return
     * The User object of the current user.
     */
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

    /**
     * Getter for the userList
     * @return userList
     * ArrayList holding all users of the application.
     */
    public HashMap<String, User> getUserList() {
        return userList;
    }

    /**
     * Creates a new user by calling the User constructor and adding it to the database.
     * @param userID
     * The android id that is unique to a device.
     * @param userName
     * User name of the new user
     */
    public void newUser(String userID, String userName) {
        database.addUserData(new User(userID, userName));
        this.userID = userID;
    }

    /**
     * Fetches the user data from the database. userList is refreshed.
     */
    public void fetchData() {
        userList = database.fetchUserData();
    }

    /**
     * Setter for the user id
     * @param androidId
     * The android id that is unique to a device.
     */
    public void setUserId(String androidId) {
        this.userID = androidId;
    }
}
