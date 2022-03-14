package com.arrayliststudent.qrhunt;

import java.util.Observer;

public class MainPresenter {

    MainPresenter() {
        UserDataModel model = UserDataModel.getInstance();
        model.fetchData();
    }

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

    public void newUser( String androidId, String name) {
        UserDataModel model = UserDataModel.getInstance();
        model.newUser(androidId, name);
    }
}
