package com.arrayliststudent.qrhunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * @author Kieran
 *
 * Fragment for adding a comment. Launched from comment activity and (currently) sends comment
 * body to Comment activity.
 */
public class AddCommentFragment extends DialogFragment {

    private EditText addComment;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener{
        void onCommentOkPressed(String commentBody);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof AddCommentFragment.OnFragmentInteractionListener)
        {
            listener = (AddCommentFragment.OnFragmentInteractionListener) context;
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_comment,null);

        addComment = view.findViewById(R.id.comment_frag);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Comment")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String commentBody = addComment.getText().toString();
                        listener.onCommentOkPressed(commentBody);
                    }
                }).create();

    }
}
