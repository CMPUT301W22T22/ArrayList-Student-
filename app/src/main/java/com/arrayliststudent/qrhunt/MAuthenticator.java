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

    private MAuthenticator() { }

    public static void set_flag(boolean flag) {
        MAuthenticator.successFlag = flag;
    }

    public boolean login(String androidId) {
        UserDataModel model = UserDataModel.getInstance();
        HashMap<String, User> userList = model.getUserList();
        for (Map.Entry<String, User> pair : userList.entrySet()) {
            System.out.println(pair.getKey() + " " );
        }
        if (userList.containsKey(androidId)) {
            System.out.println("user id " + androidId + " found");
            this.android_id = androidId;
            model.setUserId(androidId);
            return true;
        } else {
            System.out.println("user id " + androidId + " not found");
            return false;
        }

    }

}
