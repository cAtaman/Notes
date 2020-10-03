//package com.cAtaman.notes;
//
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.selection.ItemDetailsLookup;
//import androidx.recyclerview.widget.RecyclerView;
//
//final class NotesDetailsLookup extends ItemDetailsLookup {
//
//    private final RecyclerView mRecyclerView;
//
//    NotesDetailsLookup(RecyclerView recyclerView) {
//        mRecyclerView = recyclerView;
//    }
//
//    public @Nullable
//    ItemDetails getItemDetails(@NonNull MotionEvent e) {
//        View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
//        if (view != null) {
//            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
//            if (holder instanceof NotesAdapter.NotesViewHolder) {
//                return ((NotesAdapter.NotesViewHolder) holder).getItemDetails();
//            }
//        }
//        return null;
//    }
//}