package com.arrayliststudent.qrhunt;

import java.util.Observer;

//Same as CodeListPresenter for now
public class UserSearchPresenter {

    public void refresh() {
        UserDataModel model = UserDataModel.getInstance();
        model.refresh();
    }
    public void setUpObserver(Observer arg) {
        UserDataModel model = UserDataModel.getInstance();
        model.addObserver(arg);
    }
}
