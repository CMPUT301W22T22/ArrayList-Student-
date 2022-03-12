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
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        UserDataModel model = UserDataModel.getInstance();
        ArrayList<User> users = (ArrayList<User>) model.getUsers();

        //holder.getUsername().setText(users.get(position).getUsername());

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