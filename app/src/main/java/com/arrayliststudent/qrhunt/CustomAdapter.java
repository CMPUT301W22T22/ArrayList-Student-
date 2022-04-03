package com.arrayliststudent.qrhunt;

import android.widget.TextView;

public class CustomAdapter {
    private final TextView text1;
    private final TextView text2;
    private final TextView text3;

    CustomAdapter(TextView text1, TextView text2, TextView text3) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }

    public void updateData() {
        UserDataModel model = UserDataModel.getInstance();
        User currentUser = model.getCurrentUser();
        text1.setText(currentUser.getName());
        text2.setText(String.valueOf("Score: " + currentUser.getTotalScore()));
        text3.setText(String.valueOf("Codes: " +currentUser.getNumCodes()));
    }

}
