package com.arrayliststudent.qrhunt;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.Context;

public class HighScorePresenter {
    private HighScorePresenter.OnGetDataListener listener;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    final String TAG = "dunno what to put here";

    public HighScorePresenter(){
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Users");
        this.listener = null;
    }

    public interface OnGetDataListener{
        void refreshRecyclerView(HashMap<String,User> userHashMap);
    }

    public void setListener(OnGetDataListener listener) {
        this.listener = listener;
    }

    public void UpdateData(){
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                // Iterate over all documents in the collection
                HashMap<String,User> userHashMap = new HashMap<>();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String userId = new String();
                    String name = new String();
                    String contactInfo = new String();
                    List<Map> userCodeList = null;
                    Map<String,Object> user = doc.getData();
                    User addedUser;
                    Integer totalScore = -1;
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
                        if (pair.getKey().equals("totalScore")) {
                            totalScore = ((Long) pair.getValue()).intValue();

                        }
                        if (pair.getKey().equals("userCodeList")) {
                            userCodeList = (List) pair.getValue();

                        }
                    }

                    // Add the user from the current document to userDataList
                    if(success) {
                        addedUser = new User(userId, name, contactInfo);
                        addedUser.setTotalScore(totalScore);
                        if(userCodeList != null) {
                            addedUser.setCodeList(userCodeList);
                        }
                        userHashMap.put(userId, addedUser);
                        Log.d(TAG, "User " + userId + " downloaded");
                    }
                }
                if (userHashMap!=null){
                    listener.refreshRecyclerView(userHashMap);
                }
            }
        });
    }
}
