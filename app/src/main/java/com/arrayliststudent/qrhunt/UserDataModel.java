package com.arrayliststudent.qrhunt;

import android.os.Handler;
import android.view.View;

import com.google.firebase.firestore.core.Query;

import java.io.InputStream;

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

    /**
     * Converts the userList of Maps taken from the database into an ArrayList of Users
     * @return
     * The list of Users as an ArrayList instead of HashMap
     */
    public ArrayList<User> getUserDataList() {
        ArrayList<User> userDataList = new ArrayList<>();
        for (Map.Entry<String, User> pair : userList.entrySet()) {
            userDataList.add(pair.getValue());
        }
        return userDataList;
    }



    /**
     * Adds a new ScannableCode to the users code list, updates their score, updates the
     * User's document in the database, adds the Code to the database ScannableCode collection,
     * and notifys all observers so those views have the correct data to display.
     * @param code
     * ScannableCode to be added to the data model
     */
    public void addCode(ScannableCode code) {
        User user = getCurrentUser();
        user.addToScore(code.getCodeScore());
        user.addToNumCodes();
        user.addCode(code);
        setUserCodes();
        database.addUserData(user);
        database.addCode(code);
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

    /**
     * Getter for the app Users
     * @return
     * The list of Users as a Collection instead of HashMap
     */
    public Collection<User> getUsers() {
        return userList.values();
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
     * Edits a given User document by removing the original User, and adding back the same User
     * but with updated fields.
     * @param user
     * Edited user to be added
     * @param user_remove
     * Original user to be removed
     */
    public void editUser(User user,User user_remove){
        database.removerUserData(user_remove);
        database.addUserData(user);
        setChanged();
        notifyObservers();
    }

    /**
     * Removes a User document from the Users collection
     * @param user
     * User to be removed
     */
    public void removeUser(User user){
        database.removerUserData(user);
    }

    /**
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

    /**
     * Setter for the current user of the application
     * @param user
     * User currently logged in to the application
     */
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

    /**
     * Notify observers that the data has changed so those views can display the correct data
     */
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

    /**
     * Gets the User object from the Users collection according to an android id
     * @param androidId
     * The android id that is unique to a device.
     * @return
     * The User corresponding to that android id
     */
    public User getUserById(String androidId) {
        return database.getUserById(androidId);
    }

    /**
     * Gets the User object from the Owners collection according to an android id
     * @param androidId
     * The android id that is unique to a device.
     * @return
     * The User corresponding to that android id
     */
    public User getOwnerById(String androidId) {
        return database.getOwnerById(androidId);
    }

    /**
     * Setter for the current user of the application
     * @param androidId
     * The android id corresponding to the current user logged into the application.
     */
    public void fetchCurrentUser(String androidId) {
        this.currentUser = database.getUserById(androidId);
    }

    /**
     * Setter for the code list belonging to the current user to be stored
     * in the model for the CodeListActivity. Each code is retrieved from the database
     * according to the data contained in the userCodeList.
     */
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

    /**
     * Getter for the code list belonging to the current user to be stored
     * @return
     * ArrayList of ScannableCodes belonging to the current user
     */
    public ArrayList<ScannableCode> getUserCodes() {
        return this.userCodes;
    }

    /**
     * Getter for the code data belonging to the current user
     * @return
     * ArrayList of code data belonging to the current user
     */
    public ArrayList<Map> getUserCodeList() {
        List<Map> localDataset = currentUser.getUserCodeList();
        ArrayList<Map> codeList = new ArrayList(localDataset);
        return codeList;
    }

    /**
     * Setter for ScannableCode saved by the CodeListActivity, to be used in the QRCodeActivity
     * @param code
     * ScannableCode to be saved
     */
    public void saveCode(ScannableCode code) {
        this.savedCode = code;
    }

    /**
     * Getter for ScannableCode saved by the CodeListActivity, to be used in the QRCodeActivity
     * @return
     * Code that was saved
     */
    public ScannableCode getSavedCode() {
        return savedCode;
    }

    /**
     * Getter for ArrayList of ScannableCodes store in the ScannableCodes Collection
     * @return
     * ArrayList of ScannableCodes from ScannableCodes Collection
     */
    public ArrayList<ScannableCode> getAllCodes() {
        return database.getAllCodeData();
    }

    /**
     * Adds the current user as an owner of the app. Stores the android id and user name
     * in the Onwers collection.
     * @param android_id
     * Android Id of user to be added as an owner.
     */
    public void addOwner(String android_id) {
        database.addOwner(android_id, currentUser.getName());
    }

    /**
     * Removes the current user as an owner of the app.
     * @param android_id
     * Android Id of user to be removed as an owner.
     */
    public void removeOwner(String android_id) {
        database.removeOwner(android_id);
    }

    /**
     * Removes the data for a given code from each user's userCodeList in the provided
     * ArrayList of users.
     * @param users
     * ArrayList of Users with given code to be removed.
     * @return
     * ArrayList of users with given code removed.
     */
    public ArrayList<User> removeCodeFromUsers(ArrayList<User> users, String id) {
        for(User user : users) {
            ArrayList<Map> codeList = (ArrayList<Map>) user.getUserCodeList();
            Iterator iterator = codeList.iterator();
            while (iterator.hasNext()) {
                Map<String,Object> m = (Map<String, Object>) iterator.next();
                for (Map.Entry<String,Object> entry : m.entrySet()) {
                    String key = entry.getKey();
                    if(key.equals("id")) {
                        if(entry.getValue().equals(id)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        return users;
    }

    /**
     * Removes the ScannableCode from current user's userCodeList, updates the users score,
     * removes that user from the database, and adds the user back with the updated userCodeList.
     * Then gets all Users from the database who have the same code, removes that code from their
     * UserCodeList and adds back those users.
     * @param id
     * id of Scannable code to be removed
     * @id position
     * position of code to be removed from userCodeList and userCodes
     */
    public void deleteUserCodes(String id, int position) {
        currentUser.deleteCode(position);
        currentUser.setTotalScore(
                currentUser.getTotalScore()-userCodes.get(position).getCodeScore());
        currentUser.setNumCodes(currentUser.getNumCodes()-1);
        database.removerUserData(currentUser);
        database.addUserData(currentUser);
        userCodes.remove(position);
        ArrayList<User> users = database.getUsersByCode(id);
        ArrayList<User> newUsers;
        newUsers = removeCodeFromUsers(users, id);
        database.addUsers(newUsers);
        setChanged();
        notifyObservers();
    }

    /**
     * Removes a given ScannableCode from the ScannableCode collection
     * @param id
     * id of Scannable code to be removed
     */
    public void deleteCode(String id) {
        database.deleteCode(id);
    }

    /**
     * Getter for code position saved from CodeListActivity
     * @return
     * position that was saved.
     */
    public int getCodePosition() {
        return codePosition;
    }

    /**
     * Setter for code position saved from CodeListActivity
     * @param position
     * position to be saved.
     */
    public void setCodePosition(int position) {
        this.codePosition = position;
    }
}
