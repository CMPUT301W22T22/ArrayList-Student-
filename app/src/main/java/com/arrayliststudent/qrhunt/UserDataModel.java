package com.arrayliststudent.qrhunt;


import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


public class UserDataModel extends Observable {

    HashMap<Integer, User> userList;

    private UserDataModel() {
        this.userList = new HashMap<>();
    }

    private static final UserDataModel userDataModel = new UserDataModel();

    private Integer userID;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    public static UserDataModel getInstance() {
        return userDataModel;
    }

    public ArrayList<ScannableCode> getLocalData() {
        return userList.get(userID).getUserCodeList();
    }

    public void addCode(ScannableCode code) {
        userList.get(this.userID).getUserCodeList().add(code);
        setChanged();
        notifyObservers();
    }

    public String getCurrentUserName() {
        return "username";
    }

    public User getCurrentUser() {
        return userList.get(userID);
    }

    public void newUser(Integer userID, String userName) {
        this.userID = userID;
        userList = new HashMap<>();
        if (!userList.containsKey(userID)) {
            userList.put(userID, new User(userID, userName));
        }
    }



    public void newUser(String name, String androidId) {
        User user = new User(androidId, name);
        Map<String, Object> userData = new HashMap<>();
        String[] codenames = new String[0];
        userData.put("codeList", codenames);
        userData.put("id", "androidId");
        userData.put("name", name);

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FireStore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FireStore", "Error adding document", e);
                    }
                });

    }
}
