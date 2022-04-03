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
import java.util.List;
import java.util.Map;

/**
 * Custom list for a user's codes
 * Extends CustomRVAdapter and uses most of it's content
 *
 * @author jmgraham
 * @see CustomRVAdapter
 * @see ScannableCode
 * @see CodeListActivity
 */


public class CodeListAdapter extends RecyclerView.Adapter<CodeListAdapter.ViewHolder> {

        RVClickListener listener;

        CodeListAdapter(RVClickListener listener) {
            this.listener = listener;

        }

        // Create View Holder and inflate view
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_content, parent, false);
            return new ViewHolder(view);
        }

        // Bind data to view holder

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            UserDataModel model = UserDataModel.getInstance();
            ArrayList<ScannableCode> localUserCodes = model.getUserCodes();
            ArrayList<Map> localCodeList = model.getUserCodeList();

            Map<String, String> dataMap = localCodeList.get(position);
            for (Map.Entry<String, String> pair : dataMap.entrySet()) {
                String key = pair.getKey();
                if (key.equals("codeName")) {
                    holder.getCodeName().setText(pair.getValue());
                }
            }

            ScannableCode code = localUserCodes.get(position);
            holder.getCodeScore().setText(String.valueOf(code.getCodeScore()));


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
            if (model.getUserCodeList() != null) {
                return model.getUserCodeList().size();
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