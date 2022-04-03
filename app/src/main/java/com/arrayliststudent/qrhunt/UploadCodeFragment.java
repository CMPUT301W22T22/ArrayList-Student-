package com.arrayliststudent.qrhunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class UploadCodeFragment extends DialogFragment {
    private UploadCodeFragment.OnFragmentInteractionListener listener;
    private int score;
    private EditText name_edit;
    private TextView Score_view;
    private Switch aSwitch;


    public interface OnFragmentInteractionListener{
        void onUploadPressed(String codeName, Boolean location_info);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof UploadCodeFragment.OnFragmentInteractionListener)
        {
            listener = (UploadCodeFragment.OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    +"must implement OnFragmentInteractionListener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_upload_code,null);
        name_edit = view.findViewById(R.id.uploadcode_edit_name);
        Score_view = view.findViewById(R.id.uploadcode_score);
        aSwitch = view.findViewById(R.id.location_switch);
        name_edit.setText("Code Name");
        Score_view.setText(String.valueOf(score));


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Upload Code")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onUploadPressed(name_edit.getText().toString(),aSwitch.isChecked());
                    }
                }).create();

    }
    public UploadCodeFragment(int score){
        this.score = score;
    }
}
