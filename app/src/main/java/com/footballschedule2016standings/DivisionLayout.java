package com.footballschedule2016standings;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.footballschedule2016.logify.R;


/**
 * Created by Scott on 11/23/2015.
 */
public class DivisionLayout extends LinearLayoutCompat {

    private TextView divisionNameTextView;
    private LinearLayout teamLayoutLinearLayout;

    public DivisionLayout(Context context,Division division) {
        super(context);

        LayoutInflater.from(getContext()).inflate(R.layout.division_layout, this, true);

        divisionNameTextView = (TextView) this.findViewById(R.id.divisionNameTextView);
        teamLayoutLinearLayout = (LinearLayout) this.findViewById(R.id.teamsLinearLayout);

        divisionNameTextView.setText(division.getName());

        for(Team team: division.getTeams()){
            teamLayoutLinearLayout.addView(new TeamLayout(getContext(),team));
        }

    }
}
