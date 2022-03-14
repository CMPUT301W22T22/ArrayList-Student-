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
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    FirebaseFirestore database;
    CollectionReference collectionReference;

    public FirebaseData() {
        database = FirebaseFirestore.getInstance();
        collectionReference = database.collection("Users");
    }

    public void addUserData(User user){//adds or overwrites User data on the firebase

        collectionReference
                .document(user.getUserId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //if data is successfully uploaded
                        Log.d(TAG, "User " + user.getUserId() + "  successfully uploaded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //if data upload fails
                        Log.d(TAG, "User " + user.getUserId() + " data failed to upload: " + e.toString());
                    }
                });
    }

    public void removerUserData(User user){//removes User data from the firebase
        collectionReference
                .document(user.getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //if data is successfully uploaded
                        Log.d(TAG, "User " + user.getUserId() + "  successfully deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //if data upload fails
                        Log.d(TAG, "User " + user.getUserId() + " data failed to delete: " + e.toString());
                    }
                });
    }

    public HashMap<String, User> fetchUserData() {
        HashMap<String, User> userDataList = new HashMap<>();

        //snapshot listener to watch for changes in the database
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {


                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String userId = new String();
                    String name = new String();
                    ArrayList<ScannableCode> userCodeList;
                    Map<String,Object> user = doc.getData();
                    boolean success = false;
                    for (Map.Entry<String, Object> pair : user.entrySet()) {

                        String key = pair.getKey();

                        if (pair.getKey().equals("userId")) {
                            System.out.println((String) pair.getValue());
                            userId = (String) pair.getValue();
                            success = true;
                        }
                        if (pair.getKey().equals("name")) {
                            System.out.println((String) pair.getValue());
                            name = (String) pair.getValue();
                            success = true;
                        }
//                        if (pair.getKey() == "userCodeList") {
//                            userCodeList = new ArrayList<ScannableCode>(pair.getValue());
//                        }
                    }
                    if(success) {
                        userDataList.put(userId, new User(userId, name));
                        Log.d(TAG, "User " + userId + " downloaded");
                    }

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