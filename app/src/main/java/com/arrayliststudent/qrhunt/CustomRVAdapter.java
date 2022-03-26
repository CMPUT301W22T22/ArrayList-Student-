package com.arrayliststudent.qrhunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.ViewHolder> {

    RVClickListener listener;

    CustomRVAdapter(RVClickListener listener) {
        this.listener = listener;

    }

    // Create View Holder and inflate view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_content, parent, false);
        return new ViewHolder(view);    }

    // Bind data to view holder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        UserDataModel model = UserDataModel.getInstance();
        ArrayList<User> data = model.getUserDataList();

//        holder.getCodeName().setText(data.get(position).getCodeName());
//        holder.getCodeScore().setText(data.get(position).getCodeScore());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(holder.itemView, holder.getAdapterPosition());
            else
                throw new IllegalStateException("Click listener not set");
        });
    }

    // Return number of items in Adapter
    @Override
    public int getItemCount() {
        UserDataModel model = UserDataModel.getInstance();
        if (model.getUserDataList() != null) {
            return model.getUserDataList().size();
        } else {
            return 0;
        }
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    // ViewHolder for Recycler View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView codeName;
        private final TextView codeScore;

        public ViewHolder(View view) {
            super(view);
            codeName = view.findViewById(R.id.codelist_text_codename);
            codeScore = view.findViewById(R.id.codelist_text_codescore);
        }

        public TextView getCodeName() {
            return codeName;
        }
        public TextView getCodeScore() {
            return codeScore;
        }
    }
}
