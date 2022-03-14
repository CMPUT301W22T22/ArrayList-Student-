package com.arrayliststudent.qrhunt;

import java.util.HashMap;
import java.util.Observer;

public class UserProfilePresenter {
    private UserDataModel model;
    HashMap<String, User> userHashMap;
    UserProfilePresenter() {
        model = UserDataModel.getInstance();
        //model.fetchData();
        this.userHashMap = model.getUserList();
    }

    public HashMap<String, User> getUsers(){
        return userHashMap;
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

    public void removeUser(User user){
        UserDataModel model = UserDataModel.getInstance();
        model.removeUser(user);
    }

    public void editUser(User user,User user_remove){
        UserDataModel model = UserDataModel.getInstance();
        model.fetchData();
        model.editUser(user,user_remove);
    }

}
