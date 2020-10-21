package com.cAtaman.notes;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private ArrayList mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotesAdapter(ArrayList myDataset) {
        mDataset = myDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView mText;
        public TextView mTimestamp;
        public NotesViewHolder(LinearLayout v) {
            super(v);
            mText = (TextView) v.findViewById(R.id.note_text_short);
            mTimestamp = (TextView) v.findViewById(R.id.note_timestamp);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);

        NotesViewHolder vh = new NotesViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mText.setText((String) ((HashMap) mDataset.get(position)).get("content"));
        holder.mTimestamp.setText((String) ((HashMap) mDataset.get(position)).get("datetime"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}