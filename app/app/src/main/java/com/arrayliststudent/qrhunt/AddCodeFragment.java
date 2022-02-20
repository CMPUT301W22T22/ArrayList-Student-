package com.arrayliststudent.qrhunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class AddCodeFragment extends DialogFragment {

    private AddCodeFragment.OnFragmentInteractionListener listener;

    EditText codeNameEditText;
    EditText codeScoreEditText;

    public interface OnFragmentInteractionListener {
        void onOkPressed(ScannableCode code);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddCodeFragment.OnFragmentInteractionListener) {
            listener = (AddCodeFragment.OnFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_code, null); //  Sets up the view for the add/edit experiment window
        codeNameEditText = view.findViewById(R.id.addcode_edit_name);
        codeScoreEditText = view.findViewById(R.id.addcode_edit_score);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("New Game")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // Set new game fields and pass back to listener
                        ScannableCode code = new ScannableCode();
                        String temp = codeNameEditText.getText().toString();
                        boolean pass = true;
                        // Check name length
                        if (temp.length() < 41) {
                            // Set name
                           code.setCodeName(temp);
                        } else {
                            // Name too long
                            pass = false;
                            Toast.makeText(getActivity(),"Name is too long, name should be less than 40 characters", Toast.LENGTH_SHORT).show();
                        }

                        temp = codeScoreEditText.getText().toString();
                        Integer temp2;
                        if(temp.length() > 0) {
                            temp2 = Integer.valueOf(temp);
                            if (temp2 > 0 && temp2 < 2147483647) {
                                code.setCodeScore(temp);
                            } else {
                                // Invalid roll number
                                pass = false;
                                Toast.makeText(getActivity(), "Score number too large or negative", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter Score number", Toast.LENGTH_SHORT).show();
                        }

                        if (pass) {
                            listener.onOkPressed(code);
                        }
                    }
                }) // New user is created with new arguments on the press of the "ok" button.
                .create();
    }
}
