package com.arrayliststudent.qrhunt;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    CollectionReference codeCollection;

    private static boolean successFlag;

    public FirebaseData() {
        database = FirebaseFirestore.getInstance();
        collectionReference = database.collection("Users");
        codeCollection = database.collection("ScannableCodes");
    }

    public void addUserData(User user){//adds or overwrites User data on the firebase

        Map<String, Object> userData = new HashMap<>();
        userData.put("contactInfo", user.getContactInfo());
        userData.put("name", user.getName());
        userData.put("numCodes", user.getNumCodes());
        userData.put("totalScore", user.getTotalScore());
        userData.put("userCodeList", user.getUserCodeList());
        userData.put("userId", user.getUserId());

        collectionReference
                .document(user.getUserId())
                .set(userData)
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

    public void addCode(ScannableCode code) {
        // When a new code is added is must be added to the ScannableCodes collection and appended
        // to the codeList of the current user document from the Users collection

        // Add to ScannableCodes collection
        // First check if the code already exists

        Map<String, Object> codeData = new HashMap<>();
        codeData.put("codeName", code.getCodeName());
        codeData.put("codeScore", code.getCodeScore());


        codeCollection
                .document(code.getId())
                .set(codeData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //if data is successfully uploaded
                        Log.d(TAG, "Code " + code.getId() + "  successfully uploaded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //if data upload fails
                        Log.d(TAG, "User " + code.getId() + " data failed to upload: " + e.toString());
                    }
                });


    }


    public User getUserData(String userId) {

        User user = new User();

        DocumentReference docRef = database.collection("Users").document(userId);

        // https://cloud.google.com/firestore/docs/query-data/get-data
        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.SERVER;

        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found on the server
                    DocumentSnapshot document = task.getResult();
                    Map<String,Object> userData = document.getData();
                    for (Map.Entry<String, Object> pair : userData.entrySet()) {
                        String key = pair.getKey();

                        if (pair.getKey().equals("userId")) {
                            System.out.println((String) pair.getValue());
                            user.setId((String) pair.getValue());
                        }
                        if (pair.getKey().equals("name")) {
                            System.out.println((String) pair.getValue());
                            user.setName((String) pair.getValue());
                        }
                        if (pair.getKey().equals("contactInfo")){
                            System.out.println((String) pair.getValue());
                            user.setContactInfo((String) pair.getValue());
                        }
                        if (pair.getKey() == "userCodeList") {
                            user.setCodeList((List) pair.getValue());
                        }
                    }
                    Log.d(TAG, "User " + userId + " downloaded");
                    Log.d(TAG, "Server document data: " + document.getData());
                } else {
                    Log.d(TAG, "Server get failed: ", task.getException());
                }
            }
        });
        return user;
    }

    public ScannableCode getCode(String id) {
        ScannableCode code = new ScannableCode();
        DocumentReference docRef = database.collection("ScannableCodes").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String,Object> user = document.getData();
                        for (Map.Entry<String, Object> pair : user.entrySet()) {
                            String key = pair.getKey();
                            if (key.equals("codeName")) {
                                System.out.println((String) pair.getValue());
                                code.setCodeName((String) pair.getValue());
                            }
                            if (key.equals("codeScore")) {
                                System.out.println((String) pair.getValue());
                                code.setCodeScore(Integer.valueOf((String) pair.getValue()));
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return code;
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


    // Returns a list of every User from the the "Users" collection
    public HashMap<String, User> getAllUserData() {
        HashMap<String, User> userDataList = new HashMap<>();
        //snapshot listener to watch for changes in the database
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                // Iterate over all documents in the collection
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String userId = new String();
                    String name = new String();
                    String contactInfo = new String();
                    List<String> userCodeList = null;
                    Map<String,Object> user = doc.getData();
                    User addedUser;
                    boolean success = false;
                    // Get all fields from the current document and construct a User
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
                        if (pair.getKey().equals("contactInfo")){
                            System.out.println((String) pair.getValue());
                            contactInfo = (String) pair.getValue();
                            success = true;
                        }
                        if (pair.getKey() == "userCodeList") {
                            userCodeList = (List) pair.getValue();

                        }
                    }

                    // Add the user from the current document to userDataList
                    if(success) {
                        addedUser = new User(userId, name, contactInfo);
                        if(userCodeList != null) {
                            addedUser.setCodeList(userCodeList);
                        }
                        userDataList.put(userId, addedUser);
                        Log.d(TAG, "User " + userId + " downloaded");
                    }
                }
            }
        });
        return userDataList;
    }

   public boolean checkForUser(String androidId) {
        successFlag = false;
        DocumentReference docRef = database.collection("Users").document(androidId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       successFlag = true;
                       Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                   } else {
                       Log.d(TAG, "No such document");
                   }
               } else {
                   Log.d(TAG, "get failed with ", task.getException());
               }
           }
       });
       return successFlag;
   }

   public boolean checkForCode(String name) {
       successFlag = false;
       DocumentReference docRef = database.collection("ScannableCodes").document(name);
       docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                       successFlag = true;
                   } else {
                       Log.d(TAG, "No such document");
                   }
               } else {
                   Log.d(TAG, "get failed with ", task.getException());
               }
           }
       });
       return successFlag;
   }

   public ArrayList<User> getUsersByName(String userName){
       ArrayList<User> userDataList = new ArrayList<>();
       //snapshot listener to watch for changes in the database
       database.collection("Users")
               .whereEqualTo("name", userName)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               User user = new User();
                               Map<String,Object> userData = document.getData();
                               // Get all fields from the current document and construct a User
                               for (Map.Entry<String, Object> pair : userData.entrySet()) {

                                   String key = pair.getKey();
                                   if (key.equals("userId")) {
                                       System.out.println((String) pair.getValue());
                                       user.setId((String) pair.getValue());
                                   }
                                   if (pair.getKey().equals("name")) {
                                       System.out.println((String) pair.getValue());
                                       user.setName((String) pair.getValue());
                                   }
                                   if (pair.getKey().equals("contactInfo")){
                                       System.out.println((String) pair.getValue());
                                       user.setContactInfo((String) pair.getValue());
                                   }
                                   if (pair.getKey() == "userCodeList") {
                                       user.setCodeList((List) pair.getValue());
                                   }
                               }
                               // Add the user from the current document to userDataList
                               if(user.getUserId() != null && user.getName() != null && user.getUserCodeList() != null
                                       && user.getContactInfo() != null) {
                                   userDataList.add(user);
                                   Log.d(TAG, "User " + user.getUserId() + " downloaded");
                               } else {
                                   Log.d(TAG, "User " + user.getUserId() + " not downloaded");

                               }
                           }
                       } else {
                           Log.d(TAG, "Error getting documents: ", task.getException());
                       }
                   }
               });
       return userDataList;
   }

    public ArrayList<User> getUsersByCode(String codeId){
        ArrayList<User> userDataList = new ArrayList<>();
        //snapshot listener to watch for changes in the database
        database.collection("Users")
                .whereArrayContains("userCodeList", codeId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = new User();
                                Map<String,Object> userData = document.getData();
                                // Get all fields from the current document and construct a User
                                for (Map.Entry<String, Object> pair : userData.entrySet()) {

                                    String key = pair.getKey();
                                    if (key.equals("userId")) {
                                        System.out.println((String) pair.getValue());
                                        user.setId((String) pair.getValue());
                                    }
                                    if (pair.getKey().equals("name")) {
                                        System.out.println((String) pair.getValue());
                                        user.setName((String) pair.getValue());
                                    }
                                    if (pair.getKey().equals("contactInfo")){
                                        System.out.println((String) pair.getValue());
                                        user.setContactInfo((String) pair.getValue());
                                    }
                                    if (pair.getKey() == "userCodeList") {
                                        user.setCodeList((List) pair.getValue());
                                    }
                                }
                                // Add the user from the current document to userDataList
                                if(user.getUserId() != null && user.getName() != null && user.getUserCodeList() != null
                                        && user.getContactInfo() != null) {
                                    userDataList.add(user);
                                    Log.d(TAG, "User " + user.getUserId() + " downloaded");
                                } else {
                                    Log.d(TAG, "User " + user.getUserId() + " not downloaded");

                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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