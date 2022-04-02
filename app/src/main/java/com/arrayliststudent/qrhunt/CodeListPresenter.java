package com.arrayliststudent.qrhunt;

import java.util.Observer;

public class CodeListPresenter {

    public void refresh() {
        UserDataModel model = UserDataModel.getInstance();
        model.refresh();
    }
    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }
}
