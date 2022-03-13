package com.arrayliststudent.qrhunt;

import java.util.Observer;

public class MainPresenter {

    public void addNewCode(ScannableCode code) {
        UserDataModel model = UserDataModel.getInstance();
        model.addCode(code);
    }

    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }

    public void removeObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.deleteObserver(arg);
    }

    public void newUser(String name, String androidId) {
        UserDataModel model = UserDataModel.getInstance();
        model.newUser(name, androidId);
    }
}
