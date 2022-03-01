package com.arrayliststudent.qrhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom list for a user's codes
 * Refactored from ListyCity
 *
 * @author jmgraham
 */
public class CodeList extends ArrayAdapter<> {
    private ArrayList<> codes;
    private Context context;

    public UserList(Context context, ArrayList<> codes){
        super(context, 0, codes);
        this.codes = codes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.code_list_content, parent, false);
        }

        /* To be implemented when code class is done
        Code code = codes.get(position);

        TextView code = view.findViewById(R.id.username_text);

        code.setText(code.getName());

        return view;
         */
    }
}
