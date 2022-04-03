package com.arrayliststudent.qrhunt;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class UserSearchFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener{

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserSearchFragment.OnFragmentInteractionListener)
        {
            listener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    +"must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_search,null);
        TextView nameText = view.findViewById(R.id.name_text);
        TextView scoreText = view.findViewById(R.id.score_text);
        TextView codeText = view.findViewById(R.id.code_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setPositiveButton("OK",null).create();
    }
    public UserSearchFragment(String userName){
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = ff.collection("Users");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: value) {
                    User user = new User();
                    Map<String,Object> code = doc.getData();
                    for(Map.Entry<String, Object> pair : code.entrySet()) {
                        if (pair.getValue().equals(userName)) {
                            System.out.println();
                        }
                    }
                }
            }
        });
    }
}
