package com.arrayliststudent.qrhunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.File;

public class SavedCodeFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    ScannableCode barcode;
    TextView displayName;
    TextView displayHash;
    TextView displayScore;
    TextView displayLocation;
    ImageView displayPhoto;


    public SavedCodeFragment(ScannableCode barcode) {
        this.barcode = barcode;
    }

    public interface OnFragmentInteractionListener {
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        // inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_saved_code, null);
        displayName = view.findViewById(R.id.display_name);
        displayHash = view.findViewById(R.id.display_hash);
        displayScore = view.findViewById(R.id.display_score);
        displayLocation = view.findViewById(R.id.display_location);
        displayPhoto = view.findViewById(R.id.display_photo);

        displayName.setText(barcode.getCodeName());
        displayHash.setText(barcode.getId());
        displayScore.setText(Integer.toString(barcode.getCodeScore()));
        displayLocation.setText(barcode.getLocation().toString());

        File photo = barcode.getPhotoFile();

        if (photo.exists()){
            Bitmap bitmap =  BitmapFactory.decodeFile(photo.getAbsolutePath());
            displayPhoto.setImageBitmap(bitmap);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Saved Code")
                .setPositiveButton("OK", null).create();
    }
}