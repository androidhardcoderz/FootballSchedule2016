package com.footballschedule2016standings;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 11/23/2015.
 */
public class Team implements Parcelable {

    private String id;
    private String name;
    private String market;
    private String wins;
    private String losses;
    private String teamColor;
    private int rank;

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getWin_pct() {
        return win_pct;
    }

    public void setWin_pct(String win_pct) {
        this.win_pct = win_pct;
    }

    public String getTies() {
        return ties;
    }

    public void setTies(String ties) {
        this.ties = ties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String ties;
    private String win_pct;

    public Team(){

    }


    protected Team(Parcel in) {
        id = in.readString();
        name = in.readString();
        market = in.readString();
        wins = in.readString();
        losses = in.readString();
        ties = in.readString();
        win_pct = in.readString();
        teamColor = in.readString();
        rank = in.readInt();
    }

    public void setSpecificTeamColor(String color){
        setTeamColor(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(market);
        dest.writeString(wins);
        dest.writeString(losses);
        dest.writeString(ties);
        dest.writeString(win_pct);
        dest.writeString(teamColor);
        dest.writeInt(rank);
    }

    @SuppressWarnings("unused")
    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
