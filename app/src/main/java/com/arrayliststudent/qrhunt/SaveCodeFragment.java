package com.arrayliststudent.qrhunt;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageCapture;
import androidx.fragment.app.DialogFragment;

import com.google.mlkit.vision.barcode.common.Barcode;

import java.io.File;

public class SaveCodeFragment extends DialogFragment {
    CheckBox picture;
    CheckBox GPS;
    private OnFragmentInteractionListener listener;
    ScannableCode barcode;
    File image;

    public SaveCodeFragment(ScannableCode barcode, File image) {
        this.barcode = barcode;
        this.image = image;
    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(ScannableCode barcode, File image, boolean pictureCheck, boolean GPSCheck);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.save_code_fragment, null);
        picture = view.findViewById(R.id.savePicture);
        GPS = view.findViewById(R.id.saveGPS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Save Code")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        boolean pictureCheck = FALSE;
                        boolean GPSCheck = FALSE;

                        if (picture.isChecked()){
                            pictureCheck = TRUE;
                        }

                        if (GPS.isChecked()){
                            GPSCheck = TRUE;
                        }

                    }
                }).create();
    }
}
