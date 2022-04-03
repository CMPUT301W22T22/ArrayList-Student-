package com.arrayliststudent.qrhunt;

import java.util.Observer;

public class QRPresenter {
    public void setUpObserver(Observer arg) {
    }

    public void deleteCode(ScannableCode code) {
        UserDataModel model = UserDataModel.getInstance();
        model.deleteUserCodes(code.getId(), model.getCodePosition());
        model.deleteCode(code.getId());
    }
}
