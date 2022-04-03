package com.arrayliststudent.qrhunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> implements Filterable {

    RVClickListener listener;

    UserSearchAdapter(RVClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_content, parent, false);
        System.out.println("hihi");
        return new ViewHolder(view);
    }

    // Bind view to our data - in this case, we want the list of users
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        UserDataModel model = UserDataModel.getInstance();
        ArrayList<User> localUserList = (ArrayList<User>) model.getUsers();

        for(User user : localUserList){
            holder.getUserNameTV().setText(user.getName());
            System.out.println(user.getName());
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(holder.itemView, holder.getAdapterPosition());
            else
                throw new IllegalStateException("Click listener not set");
        });
    }

    public void updateData() {
        System.out.println("update");
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        UserDataModel model = UserDataModel.getInstance();
        model.fetchData();
        if (model.getUsers() != null) {
            return model.getUsers().size();
        } else {
            return 0;
        }
    }

    //Custom UserSearch RVAdapter ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView userNameView;

        public ViewHolder(View view){
            super(view);
            //just gonna borrow this
            userNameView = view.findViewById(R.id.userlist_text_username);
        }

        public TextView getUserNameTV(){
            return userNameView;
        }
    }


    @NonNull
    public Filter getFilter() {
        throw new RuntimeException("Stub!");
    }
}
