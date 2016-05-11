package com.footballschedule2016standings;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 11/23/2015.
 */
public class ParseJSONFile {

    public static final String CONFERENCES_TAG = "conferences";
    public static final String DIVISION_TAG = "divisions";
    public static final String TEAM_TAG = "teams";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String MARKET = "market";
    public static final String OVERALL = "overall";
    public static final String WINS = "wins";
    public static final String LOSSES = "losses";
    public static final String TIES = "ties";
    public static final String WIN_PERCENTAGE = "wpct";

    private Context context;
    private ConferenceLoaded conferenceLoaded;

    public ParseJSONFile(Context context,ConferenceLoaded conferenceLoaded){
        this.context = context;
        this.conferenceLoaded = conferenceLoaded;
    }

    public List<Conference> parseStandingsJsonString(String jsonString) throws JSONException {

        List<Conference> standings = new ArrayList<>();
        JSONArray conferenceArray = new JSONObject(jsonString).getJSONArray(CONFERENCES_TAG);

        for(int i = 0; i < conferenceArray.length();i++){
            //loop through each of the conference arrays in json file
            JSONObject conferenceObject = conferenceArray.getJSONObject(i);
            Conference conference = new Conference();
            conference.setId(conferenceObject.getString(ID));
            conference.setName(conferenceObject.getString(NAME));

            JSONArray divisionArray = conferenceObject.getJSONArray(DIVISION_TAG);
            for(int d = 0; d < divisionArray.length();d++){
                JSONObject divisionObject = divisionArray.getJSONObject(d);
                Division division = new Division();
                division.setId(divisionObject.getString(ID));
                division.setName(divisionObject.getString(NAME));

                JSONArray teamArray = divisionObject.getJSONArray(TEAM_TAG);
                for(int t = 0; t < teamArray.length();t++){
                    JSONObject teamObject = teamArray.getJSONObject(t);
                    Team team = new Team();
                    team.setRank(t);
                    team.setId(teamObject.getString(ID));
                    team.setName(teamObject.getString(NAME));
                    team.setMarket(teamObject.getString(MARKET));

                    team.setWins(teamObject.getJSONObject(OVERALL).getString(WINS));
                    team.setLosses(teamObject.getJSONObject(OVERALL).getString(LOSSES));
                    team.setTies(teamObject.getJSONObject(OVERALL).getString(TIES));
                    team.setWin_pct(teamObject.getJSONObject(OVERALL).getString(WIN_PERCENTAGE));

                    division.getTeams().add(team);
                }

                conference.getDivisions().add(division);
            }

            standings.add(conference);
            conferenceLoaded.onConferenceLoaded(conference);
        }

        //showPrintedData(standings);

        return standings;
    }

    private void showPrintedData(List<Conference> standings) {

        for(Conference conference: standings){
            System.out.println(conference.getId());
            System.out.println(conference.getName());

            for(Division division: conference.getDivisions()){
                System.out.println(division.getId());
                System.out.println(division.getName());

                for(Team team: division.getTeams()){
                    System.out.println(team.getId());
                    System.out.println(team.getMarket());
                    System.out.println(team.getName());
                    System.out.println(team.getWins());
                    System.out.println(team.getLosses());
                    System.out.println(team.getTies());
                    System.out.println(team.getWin_pct());
                    System.out.println("___________________________");

                }

                System.out.println("___________________________");
            }

            System.out.println("___________________________");
        }
    }
}
