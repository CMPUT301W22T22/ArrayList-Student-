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
    private boolean ownerPrivledge;

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
        model.setUserId(this.android_id);
        //setPrivledges(user.getPublicKey());


    }


    public boolean loggedIn() {
        UserDataModel model = UserDataModel.getInstance();

        User user = model.getCurrentUser();

        if ( user.getUserId() == null) {
            System.out.println("hello");
            return  false;
        } else {
            model.setUserCodes();
            return true;
        }


    }

//    private void setPrivledges(String publicKey) {
//
//    }

}
