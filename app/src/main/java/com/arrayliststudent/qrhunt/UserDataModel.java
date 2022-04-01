package com.arrayliststudent.qrhunt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
        Map<String, String> codeData = new HashMap<>();
        codeData.put(code.getId(), code.getCodeName());
        if (!user.getUserCodeList().contains(codeData)) {
            user.addToScore(code.getCodeScore());
            user.addToNumCodes();
            System.out.println("Code Score = " + code.getCodeScore());
            System.out.println("Total Score = " + user.getTotalScore());

            user.getUserCodeList().add(codeData);
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

    public void fetchCurrentUser(String androidId) {
        this.currentUser = database.getUserById(androidId);
        System.out.println(currentUser.getNumCodes());
    }

    public List<Map> getUserCodeList() {
        return currentUser.getUserCodeList();
    }
}
