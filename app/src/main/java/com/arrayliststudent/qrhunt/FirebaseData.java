package com.arrayliststudent.qrhunt;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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
    final String TAG = "dunno what to put here";
    final FirebaseFirestore database = FirebaseFirestore.getInstance();
    final CollectionReference userReference = database.collection("Users");
    final CollectionReference codeReference = database.collection("Codes");

    public void addUserData(User user){//adds User data to the firebase and updates the app
        userReference
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

    public void createSnapshotListener(CollectionReference collectionReference){
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

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