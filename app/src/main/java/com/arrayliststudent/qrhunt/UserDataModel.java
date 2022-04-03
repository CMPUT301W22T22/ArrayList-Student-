package com.arrayliststudent.qrhunt;


import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.google.firebase.firestore.core.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * The UserDataModel is a singleton which is responsible for storing all data of the application.
 */
public class UserDataModel extends Observable {

    FirebaseData database;
    HashMap<String, User> userList;
    private String userID;
    private User currentUser;
    private ArrayList<ScannableCode> userCodes;
    private ScannableCode savedCode;
    private int codePosition;

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

    public static void getCurrentUserId() {

    }

    public ArrayList<User> getUserDataList() {
        ArrayList<User> userDataList = new ArrayList<>();
        for (Map.Entry<String, User> pair : userList.entrySet()) {
            userDataList.add(pair.getValue());
        }
        return userDataList;
    }



    public void addCode(ScannableCode code) {

        User user = getCurrentUser();
//        Map<String, String> codeData = new HashMap<>();
//
//        codeData.put(code.getId(), code.getCodeName());
        if (/*!user.getUserCodeList().contains(codeData*/true) {
            user.addToScore(code.getCodeScore());
            user.addToNumCodes();

            user.addCode(code);
            setUserCodes();
            database.addUserData(user);
            database.addCode(code);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the current user
     * @return
     * The User object of the current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public Collection<User> getUsers() {
        return userList.values();
    }

    public boolean checkUserExists(String userID) {
        User user = database.getUserById(userID);
        if (user.getUserId() == null){
            return false;
        } else {
            return true;
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

    public void editUser(User user,User user_remove){
        database.removerUserData(user_remove);
        database.addUserData(user);
        setChanged();
        notifyObservers();
    }

    public void removeUser(User user){
        database.removerUserData(user);
    }

    /**
     *
     * Creates a new user by calling the User constructor and adding it to the database.
     * @param userID
     * The android id that is unique to a device.
     * @param userName
     * User name of the new user
     */
    public void newUser(String userID, String userName) {
        User user = new User(userID, userName);
        user.setTotalScore(0);
        database.addUserData(user);
        this.userID = userID;
        setCurrentUser(user);

    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Fetches the user data from the database. userList is refreshed.
     */
    public void fetchData() {
        userList = database.getAllUserData();
        setUserCodes();
    }

    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /**
     * Setter for the user id
     * @param androidId
     * The android id that is unique to a device.
     */
    public void setUserId(String androidId) {
        this.userID = androidId;
    }

    public void setCurrentUser() {
        User user = database.getUserById(userID);
        this.currentUser = user;
    }

    public User getUserById(String androidId) {
        return database.getUserById(androidId);
    }

    public User getOwnerById(String androidId) {
        return database.getOwnerById(androidId);
    }

    public void fetchCurrentUser(String androidId) {
        this.currentUser = database.getUserById(androidId);
    }

    public void setUserCodes() {
        List<Map> localDataset = currentUser.getUserCodeList();

        ArrayList<ScannableCode> codeList = new ArrayList();

        for (Map<String, String> m : localDataset) {
            ScannableCode code = new ScannableCode();
            String codeName = new String();
            for (Map.Entry<String,String> entry : m.entrySet()) {
                String key = entry.getKey();
                if(key.equals("id")) {
                    code = database.getCode(entry.getValue());
                }
                if(key.equals("codeName")) {
                    codeName = entry.getValue();
                }

            }
            code.setCodeName(codeName);
            codeList.add(code);
        }
        this.userCodes = codeList;
    }

    public ArrayList<ScannableCode> getUserCodes() {


        return this.userCodes;
    }

    public ArrayList<Map> getUserCodeList() {
        List<Map> localDataset = currentUser.getUserCodeList();

        ArrayList<Map> codeList = new ArrayList(localDataset);


        return codeList;
    }

    public void saveCode(ScannableCode code) {
        this.savedCode = code;
    }

    public ScannableCode getSavedCode() {
        return savedCode;
    }

    public ArrayList<ScannableCode> getAllCodes() {
        return database.getAllCodeData();
    }

    public void addOwner(String android_id) {
        database.addOwner(android_id, currentUser.getName());
    }

    public void removeOwner(String android_id) {
        database.removeOwner(android_id);
    }

    public ArrayList<User> removeCodeFromUsers(ArrayList<User> users) {
        for(User user : users) {
            ArrayList<Map> codeList = (ArrayList<Map>) user.getUserCodeList();
            Iterator iterator = codeList.iterator();
            String code = new String();
            while (iterator.hasNext()) {
                Map<String,Object> m = (Map<String, Object>) iterator.next();
                for (Map.Entry<String,Object> entry : m.entrySet()) {
                    String key = entry.getKey();
                    if(key.equals("id")) {
                        iterator.remove();
                    }
                }

            }
        }
        return users;
    }

    public void deleteUserCodes(String id, int position) {
        currentUser.deleteCode(position);
        userCodes.remove(position);
        ArrayList<User> users = database.getUsersByCode(id);
        Handler handler = new Handler();
        ArrayList<User> newUsers;

        newUsers = removeCodeFromUsers(users);

        database.addUsers(newUsers);
        setChanged();
        notifyObservers();

    }

    public void deleteCode(String id) {
        database.deleteCode(id);
    }

    public int getCodePosition() {
        return codePosition;
    }

    public void setCodePosition(int position) {
        this.codePosition = position;
    }
}
