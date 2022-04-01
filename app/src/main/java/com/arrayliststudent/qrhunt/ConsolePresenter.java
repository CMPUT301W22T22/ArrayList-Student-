package com.arrayliststudent.qrhunt;

import java.util.Observer;

/**
 * The ConsolePresenter is responsible for the business logic of the ConsoleActivity. The only
 * method for this release is setUpObserver(), which adds the ConsoleActivity as an observer to the
 * UserDataModel Observable class.
 */
public class ConsolePresenter {


    ConsolePresenter() {
    }

    public void refresh() {
        UserDataModel model = UserDataModel.getInstance();
        model.refresh();
    }

    /**
     * This function is an example for how to add an Observer object to the Observable class
     * UserDataModel. notifyObservers() is called so the TextViews of the ConsoleActivity are
     * refreshed with the data pertaining to the current user.
     * @param arg
     * Observer object to be removed from Observable
     */
    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }
}
