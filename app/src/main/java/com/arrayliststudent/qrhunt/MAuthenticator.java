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
    DocumentSnapshot document;

    public static boolean successFlag;
    private static final MAuthenticator mAuthenticator  = new MAuthenticator();

    public static MAuthenticator getInstance(){
        return mAuthenticator;
    }

    private MAuthenticator() {
    }

    public void setCurrentUser(String androidId) {
        UserDataModel model = UserDataModel.getInstance();
        model.fetchCurrentUser(androidId);
        this.android_id = androidId;

    }


    public boolean login() {
        UserDataModel model = UserDataModel.getInstance();

        User user = model.getCurrentUser();

        boolean exists;
        if ( user.getUserId() == null) {
            exists = false;
        } else {
            exists = true;
        }

        if (exists) {
            System.out.println("user id found");
            model.setUserId(this.android_id);
            model.setCurrentUser();
            return true;
        } else {
            System.out.println("user id not found");
            return false;
        }

    }

}
