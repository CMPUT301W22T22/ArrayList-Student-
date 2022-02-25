package com.arrayliststudent.qrhunt;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Firebase controller; responsible for getting and setting Firebase data
 *
 * Built using the lab guide at: https://github.com/AbdulAli/FirestoreTutorialW22/
 * Firebase Docs: https://firebase.google.com/docs/guides?authuser=0&hl=en
 * Firebase Samples: https://firebase.google.com/docs/samples?authuser=0&hl=en
 *
 * @author jmgraham
 * @see User
**/
public class FirebaseData {
    final String TAG = "you're it";
    FirebaseFirestore database;

    public void addUserData(User user){
        database = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = database.collection("Users");

        collectionReference
                .document(String.valueOf(user.hashCode()))
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //if data is successfully uploaded
                        Log.d(TAG, "User data successfully uploaded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //if data upload fails
                        Log.d(TAG, "User data failed to upload: " + e.toString());
                    }
                });
    }
}

/*
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if false;
    }
  }
}*/