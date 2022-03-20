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
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                HashMap<String, User> userDataList = new HashMap<>();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String userId = new String();
                    String name = new String();
                    String contactInfo = new String();
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
                        if (pair.getKey().equals("contactInfo")){
                            System.out.println((String) pair.getValue());
                            contactInfo = (String) pair.getValue();
                            success = true;
                        }
//                        if (pair.getKey() == "userCodeList") {
//                            userCodeList = new ArrayList<ScannableCode>(pair.getValue());
//                        }
                    }
                    if(success) {
                        userDataList.put(userId, new User(userId, name, contactInfo));
                        Log.d(TAG, "User " + userId + " downloaded");
                    }
                }

                if (!userDataList.isEmpty()){
                    if(listener!=null){
                        listener.refreshRecyclerView(userDataList);
                    }
                }
            }

        });
    }
}
