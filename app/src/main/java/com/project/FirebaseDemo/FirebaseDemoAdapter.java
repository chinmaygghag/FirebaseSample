package com.project.FirebaseDemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by chinmay on 3/3/17.
 */

public class FirebaseDemoAdapter<D, F extends FirebaseDemoViewHolder> extends FirebaseRecyclerAdapter {
    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public FirebaseDemoAdapter(Class modelClass, int modelLayout, Class viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        Log.i("frost", "FirebaseDemoAdapter: ");
    }

    @Override
    protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Object model, int position) {
        Log.i("frost", "populateViewHolder: ");
        FirebaseDemoViewHolder holder = (FirebaseDemoViewHolder) viewHolder;
        DemoData demoData = (DemoData) model;
        final DatabaseReference postRef = getRef(position);
        final String postKey =  postRef.getKey();
        Log.i("frost", "populateViewHolder: "+demoData.getName());
        holder.bindToPost(demoData.getName());
    }

}
