package com.arrayliststudent.qrhunt;

import java.util.Observer;

public class MainPresenter {

    public void addNewCode(ScannableCode code) {
        UserDataModel model = UserDataModel.getInstance();
        model.addCode(code);
    }

    public void setUpUser(int userID) {
        UserDataModel model = UserDataModel.getInstance();
        model.setUserID(userID);
    }

    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }

    public void removeObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.deleteObserver(arg);
    }

}
