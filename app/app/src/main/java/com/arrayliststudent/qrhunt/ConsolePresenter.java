package com.arrayliststudent.qrhunt;

import java.util.Observer;

public class ConsolePresenter {

    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }
}
