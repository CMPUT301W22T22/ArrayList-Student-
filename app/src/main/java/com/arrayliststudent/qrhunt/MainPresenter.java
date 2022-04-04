package com.arrayliststudent.qrhunt;

import java.util.Observer;

/**
 * The MainPresenter is responsible for the business logic of the MainActivity. The methods
 * addNewCode(), setUpObserver() and removeObserver() were used only as an example for a
 * presenter class should interact with the UserDataModel. The MainActivity does not require
 * updates from the UserDataModel, so these methods will be removed in the next release. The only
 * required methods are the constructor which fetches the user data from the database, and
 * newUser() which creates a new user.
 */
public class MainPresenter {

    /**
     * This constructor instantiates the UserDataModel and calls fetchData() which fetches the
     * user data from the database.
     *
     */
    MainPresenter() {
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
}
