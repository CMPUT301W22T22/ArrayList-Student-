/*
* This java file contains some logic required by Userprofile
*
* */

package com.arrayliststudent.qrhunt;

import java.util.HashMap;
import java.util.Observer;

public class UserProfilePresenter {
    private UserDataModel model;
    HashMap<String, User> userHashMap;

    /**
     * This constructor instantiates the UserDataModel and calls fetchData() which fetches the
     * user data from the database.
     *
     */
    UserProfilePresenter() {
        model = UserDataModel.getInstance();
        //model.fetchData();
        this.userHashMap = model.getUserList();
    }

    /**
     * This function is an example for how to get userHashMap.
     * @return userHashMap
     * A userHashMap
     */
    public HashMap<String, User> getUsers(){
        return userHashMap;
    }

    /**
     * This function is an example for how to add a new ScannableCode to the app user data.
     * @param code
     * New ScannableCode to be added to the UserDataModel
     */
    public void addNewCode(ScannableCode code) {
        UserDataModel model = UserDataModel.getInstance();
        model.addCode(code);
    }

    /**
     * This function is an example for how to add an Observer object to the Observable class
     * UserDataModel
     * @param arg
     * Observer object to be removed from Observable
     */
    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }

    /**
     * This function is an example for how to remove an Observer object from the Observable class
     * UserDataModel
     * @param arg
     * Observer object to be removed from Observable
     */
    public void removeObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.deleteObserver(arg);
    }

    /**
     * Creates a new user.
     * @param androidId
     * The android id that is unique to a device.
     * @param name
     * User name of the new user
     */
    public void newUser( String androidId, String name) {
        UserDataModel model = UserDataModel.getInstance();
        model.newUser(androidId, name);
    }

    /**
     * Remove a user.
     * @param user
     * The User that need to Remove.
     */
    public void removeUser(User user){
        UserDataModel model = UserDataModel.getInstance();
        model.removeUser(user);
    }

    /**
     * Edit a user.
     * @param user
     * Changed User.
     * @param user_remove
     * Old User
     */
    public void editUser(User user,User user_remove){
        UserDataModel model = UserDataModel.getInstance();
        model.fetchData();
        model.editUser(user,user_remove);
    }

}
