package com.arrayliststudent.qrhunt;

import java.util.Observer;

/**
 * The CodeListPresneter is responsible for the business logic of the CodeListActivity.
 */
public class CodeListPresenter {

    /**
     * Tells the UserDataModel to notify observers of changed data
     */
    public void refresh() {
        UserDataModel model = UserDataModel.getInstance();
        model.refresh();
    }

    /**
     * Adds an observer to the UserDataModel observable
     * @param arg
     * Observer to be added
     */
    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }
}
