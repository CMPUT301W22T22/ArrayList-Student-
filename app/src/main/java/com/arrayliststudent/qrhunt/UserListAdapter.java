package com.arrayliststudent.qrhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ArrayList<User> users;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    UserListAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.users = users;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_list_content, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.UserName.setText(user.getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return users.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView UserName;

        ViewHolder(View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.Recycler_list_UserName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    User getItem(int id) {
        return users.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void add(User user)
    {
        if (user!=null){
            users.add(user);
            notifyItemInserted(users.size());
        }
    }

    public void remove(User user){
        if (user!=null){
            int p = users.indexOf(user);
            users.remove(user);
            notifyItemRemoved(p);
            //showUndoSnackbar();
        }

    }


    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
