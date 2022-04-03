package com.arrayliststudent.qrhunt;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MAuthenticator {
    
    private String android_id;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private boolean ownerPrivledge;

    private static final MAuthenticator mAuthenticator  = new MAuthenticator();

    public static MAuthenticator getInstance(){
        return mAuthenticator;
    }

    private MAuthenticator() {
        ownerPrivledge = false;
    }

    public void setCurrentUser(String androidId) {
        UserDataModel model = UserDataModel.getInstance();
        model.fetchCurrentUser(androidId);
        this.android_id = androidId;
        model.setUserId(this.android_id);
    }

    public boolean loggedIn() {
        UserDataModel model = UserDataModel.getInstance();

        User user = model.getCurrentUser();

        if ( user.getUserId() == null) {
            System.out.println("User Not Found");
            return  false;
        } else {
            model.setUserCodes();
            setPrivledges();
            return true;
        }
    }

    private void setPrivledges() {
        UserDataModel model = UserDataModel.getInstance();
        User user = model.getCurrentUser();
        if(user.getName() != null) {
            ownerPrivledge = true;
        } else {
            ownerPrivledge = false;
        }
    }

    public void toggleOwner() {
        UserDataModel model = UserDataModel.getInstance();
        if(ownerPrivledge) {
            model.removeOwner(android_id);
        } else {
            model.addOwner(android_id);
        }
        ownerPrivledge = !ownerPrivledge;

    }

    public boolean checkPrivledge() {
        return ownerPrivledge;
    }
}
