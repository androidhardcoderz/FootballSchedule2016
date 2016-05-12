package com.footballschedule2016.logify.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballschedule2016.logify.R;
import com.footballschedule2016.logify.TeamSelected;
import com.footballschedule2016.logify.objects.TeamListing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2/10/2016.
 */
public class TeamListingAdapter extends RecyclerView.Adapter<TeamListingAdapter.ViewHolder> {

    private List<TeamListing> names;
    private Context context;
    private TeamSelected teamSelected;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView teamImageView;
        TextView teamNameTextView;

        public ViewHolder(View v) {
            super(v);
            teamNameTextView = (TextView) v.findViewById(R.id.teamNameTextView);
            teamImageView = (ImageView) v.findViewById(R.id.teamImageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TeamListingAdapter(Context context,TeamSelected teamSelected) {
        this.context = context;
        names = new ArrayList<>();
        this.teamSelected = teamSelected;
    }

    public TeamListing getCustomObjectFromPosition(int pos){
        return names.get(pos);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TeamListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_card_view, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamSelected.selectedTeam(v);
            }
        });
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public int getIndexOfView(TeamListing teamListing){
        return names.indexOf(teamListing);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.teamNameTextView.setText(names.get(position).getName());

        if(names.get(position).getName().equals("San Francisco niners"))
            holder.teamNameTextView.setText("San Francisco 49ers");

        //load imageview with picasso downloading lib
        Picasso.with(context)
                .load(names.get(position).getImage())
                .into(holder.teamImageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return names.size();
    }

    public void addTeam(TeamListing teamListing){
        names.add(teamListing);
    }
}