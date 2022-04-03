package com.arrayliststudent.qrhunt;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentPresenter {
    private CommentPresenter.OnGetDataListener listener;
    private String codeId;
    public CommentPresenter(String id){
        this.listener = null;
        this.codeId = id;
    }

    public void setListener(CommentPresenter.OnGetDataListener listener) {
        this.listener = listener;
    }

    public interface OnGetDataListener{
        void getNewCode(ScannableCode code);
    }

    public void UpdateCodeInfo(){
        final String TAG = "dunno what to put here";
        FirebaseFirestore database = FirebaseFirestore.getInstance();;
        ScannableCode code = new ScannableCode();
        if (codeId!=null){
            String id = codeId;
            DocumentReference docRef = database.collection("ScannableCodes").document(id);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> user = document.getData();
                            code.setId(id);
                            for (Map.Entry<String, Object> pair : user.entrySet()) {
                                String key = pair.getKey();
                                if (key.equals("codeScore")) {
                                    Integer codeScore;
                                    codeScore = ((Long) pair.getValue()).intValue();
                                    code.setCodeScore((Integer) codeScore);
                                }
                                if (key.equals("Location")) {
                                    code.setLocation((List<Double>) pair.getValue());
                                }
                                if (key.equals("Comment")){
                                    List<HashMap<String,String>> list =
                                            (List<HashMap<String, String>>) pair.getValue();
                                    ArrayList<Comment> comments = new ArrayList<>();
                                    for (HashMap<String,String> map:list){
                                        comments.add(new Comment(map.get("author"),map.get("body")));
                                    }
                                    code.setComments(comments);
                                }

                            }
                            if (code!=null){
                                if (code.getComments().size()!=0){
                                    listener.getNewCode(code);
                                }
                            }
                            Log.d(TAG, "Code document exists");

                        } else {
                            Log.d(TAG, "No such code document " + id);
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

        }
    }
}
