package com.footballschedule2016.logify.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.footballschedule2016.logify.R;
import com.footballschedule2016.logify.objects.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2/29/2016.
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {

    private List<Game> mDataset = new ArrayList<>();
    private View.OnClickListener mOnClickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date,time,visitor,home;

        public ViewHolder(View v) {
            super(v);
            visitor = (TextView) v.findViewById(R.id.visitorTeamNameTextView);
            home = (TextView) v.findViewById(R.id.homeTeamNameTextView);
            date = (TextView) v.findViewById(R.id.dateGameTextView);
            time = (TextView) v.findViewById(R.id.timeGameTextView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecylerViewAdapter(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecylerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setOnClickListener(mOnClickListener);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       holder.date.setText(mDataset.get(position).getDateString());
        holder.time.setText(mDataset.get(position).getTime());
        holder.visitor.setText(mDataset.get(position).getVisitor());
        holder.home.setText(mDataset.get(position).getHome());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * @param game
     * adds a new game object to the current recycler view list
     */
    public void addItem(Game game){
        mDataset.add(game);
        notifyItemInserted(mDataset.size());
        notifyDataSetChanged();
    }

    public void clearAll(){
        mDataset.clear();
        notifyDataSetChanged();
    }


}
