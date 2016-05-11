package com.footballschedule2016.logify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 3/15/2016.
 */
public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder> {

        private List<Game> mDataset;
        private Context context;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView visitor,home,week,time,date;
            public ViewHolder(View v) {
                super(v);

                //assign variables to java from XML layout inflated
               visitor = (TextView) v.findViewById(R.id.visitorTextView);
                home = (TextView) v.findViewById(R.id.homeTextView);
                week = (TextView)v.findViewById(R.id.weekTextView);
                date = (TextView) v.findViewById(R.id.dateTextView);
                time = (TextView)v.findViewById(R.id.timeTextView);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public TeamRecyclerViewAdapter(Context context) {

            this.context = context;
            mDataset = new ArrayList<>();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public TeamRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.specific_game_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.visitor.setText(mDataset.get(position).getVisitor());
            holder.home.setText(mDataset.get(position).getHome());
            holder.week.setText("Week " + mDataset.get(position).getWeek());
            holder.date.setText(mDataset.get(position).getDateString());
            holder.time.setText(mDataset.get(position).getTime());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public void addGame(Game game){
            mDataset.add(game);
            notifyItemInserted(mDataset.size() - 1);
        }
    }
