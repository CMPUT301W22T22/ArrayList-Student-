package com.arrayliststudent.qrhunt;

import java.util.Observer;

/**
 * The QRPresenter is responsible for the business logic of the QRActivity.
 */
public class QRPresenter {

    /**
    * This method was not used, since the QRCodeActivity gets its data from a ScannableCode
    * passed through an intent, and does not need the UserDataModel
     */
    public void setUpObserver(Observer arg) {
    }

    /**
     * Deletes a given Scannable code from application Users collection and ScannableCode collection
     * @param code
     * ScannableCode to be deleted
     */
    public void deleteCode(ScannableCode code) {
        UserDataModel model = UserDataModel.getInstance();
        model.deleteUserCodes(code.getId(), model.getCodePosition());
        model.deleteCode(code.getId());
    }
}
