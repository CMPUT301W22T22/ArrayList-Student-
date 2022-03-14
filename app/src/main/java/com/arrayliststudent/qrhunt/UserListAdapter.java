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
import java.util.Collection;
import java.util.HashMap;

/**
 * Custom list for users
 * Based on CustomRVAdapter by lyury
 *
 * @author jmgraham
 * @see CustomRVAdapter
 * @see UserDataModel
 * @see UserListActivity
 */
public class UserListAdapter extends CustomRVAdapter{
    private RVClickListener listener;

    public UserListAdapter(RVClickListener listener){
        super(listener);
    private ArrayList<User> userDataList;

    public UserListAdapter(RVClickListener listener, ArrayList<User> userDataList){
        super(listener);

        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public CustomRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recycler_content, parent, false);
        return new CustomRVAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        UserDataModel model = UserDataModel.getInstance();
        ArrayList<User> users = (ArrayList<User>) model.getUsers();

        holder.getUsername().setText(users.get(position).getUserName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(holder.itemView, holder.getAdapterPosition());
            else
                throw new IllegalStateException("Click listener not set");
        });
    }

    // ViewHolder for Recycler View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView username;

        public ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.userlist_text_username);
        }

        public TextView getUsername() {
            return username;
        }
    }
}
