package com.footballschedule2016standings;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.footballschedule2016.logify.R;


/**
 * Created by Scott on 11/23/2015.
 */
public class TeamLayout extends LinearLayoutCompat {

    private TextView teamNameTextView;
    private TextView recordTextView;
    private TextView rankTextView;

    public TeamLayout(Context context,Team team) {
        super(context);

        LayoutInflater.from(getContext()).inflate(R.layout.team_row, this, true);

        teamNameTextView = (TextView) this.findViewById(R.id.teamNameTextView);
        recordTextView = (TextView) this.findViewById(R.id.teamRecordTextView);
        rankTextView = (TextView) this.findViewById(R.id.rankTextView);
        teamNameTextView.setText(getTeamFullName(team));
        recordTextView.setText(getFullRecord(team));
        rankTextView.setText((team.getRank() + 1) + "");

        //teamNameTextView.setTextColor(Color.BLACK);
        //recordTextView.setTextColor(Color.BLACK);

    }
    /*
        determines if color is dark on layout background
        if dark set colors of items to white
     */
    public boolean isColorDark(int color){

        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    private String getTeamFullName(Team team){
        return team.getMarket() + " " + team.getName();
    }

    private String getFullRecord(Team team){
        if(team.getTies().equals("0")){
            return team.getWins() + "-" + team.getLosses();
        }else{
            //ties exists!
            return team.getWins() + "-" + team.getLosses() + "-" + team.getTies();
        }

    }
}
