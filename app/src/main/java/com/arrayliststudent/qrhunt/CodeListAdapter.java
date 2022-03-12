package com.arrayliststudent.qrhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Custom list for a user's codes
 * Extends CustomRVAdapter and uses most of it's content
 *
 * @author jmgraham
 * @see CustomRVAdapter
 * @see ScannableCode
 * @see CodeListActivity
 */
public class CodeListAdapter extends CustomRVAdapter{
    RVClickListener listener;

    public CodeListAdapter(RVClickListener listener){
        super(listener);
    }
}
