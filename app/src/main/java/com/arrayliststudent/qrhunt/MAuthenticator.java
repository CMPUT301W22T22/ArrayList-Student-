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
        this.android_id = androidId;
        MAuthenticator.successFlag = false;
        db.collection("Users")
                .document(androidId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            Log.d("FireStore", "Document exists!");
                            this.document = documentSnapshot;
                            MAuthenticator.successFlag = true;
                        } else {
                            MAuthenticator.successFlag = false;
                            Log.d("FireStore", "Document does not exists!");
                        }
                    } else {
                        MAuthenticator.successFlag = false;
                        Log.d("FireStore", "Failed with: ", task.getException());
                    }
                });
        return MAuthenticator.successFlag;

    }

}
