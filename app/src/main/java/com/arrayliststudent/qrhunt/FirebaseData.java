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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

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
    final CollectionReference collectionReference = database.collection("Users");

    public FirebaseData() { }

    public void addUserData(User user){//adds or overwrites User data on the firebase
        collectionReference
                .document(String.valueOf(user.hashCode()))
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //if data is successfully uploaded
                        Log.d(TAG, "User " + user.hashCode() + "  successfully uploaded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //if data upload fails
                        Log.d(TAG, "User " + user.hashCode() + " data failed to upload: " + e.toString());
                    }
                });
    }

    public void removerUserData(User user){//removes User data from the firebase
        collectionReference
                .document(String.valueOf(user.hashCode()))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //if data is successfully uploaded
                        Log.d(TAG, "User " + user.hashCode() + "  successfully deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //if data upload fails
                        Log.d(TAG, "User " + user.hashCode() + " data failed to delete: " + e.toString());
                    }
                });
    }

    public HashMap<Integer, User> fetchUserData() {
        HashMap<Integer, User> userDataList = new HashMap<>();

        //snapshot listener to watch for changes in the database
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    int userID = doc.getData().hashCode();
                    User userData = (User) doc.getData();

                    userDataList.put(userID, userData); // Adding the cities and provinces from FireStore
                    Log.d(TAG, "User " + userID + " downloaded");
                }
            }
        });

        return userDataList;
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