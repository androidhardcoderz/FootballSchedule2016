package com.footballschedule2016.logify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2/10/2016.
 */
public class HeirachyParser {

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


    /**
     * @param num the conference index AFC = 0, NFC = 1
     * @param jsonString the string stream of standings file
     * @return conference object containt division and team names
     * @throws JSONException
     */
    public List<Conference> parseHeirarchyFile(String jsonString) throws JSONException {

        JSONArray conferenceArray = new JSONObject(jsonString).getJSONArray("conferences");

        List<Conference> conferences = new ArrayList<>();

        for(int i = 0; i < conferenceArray.length();i++) {
            //loop through each of the conference arrays in json file
            Conference conference = new Conference();
            JSONObject conferenceObject = conferenceArray.getJSONObject(i);
            conference.setId(conferenceObject.getString(ID));
            conference.setName(conferenceObject.getString(NAME));

            JSONArray divisionArray = conferenceObject.getJSONArray(DIVISION_TAG);
            for (int d = 0; d < divisionArray.length(); d++) {
                JSONObject divisionObject = divisionArray.getJSONObject(d);
                Division division = new Division();
                division.setId(divisionObject.getString(ID));
                division.setName(divisionObject.getString(NAME));

                JSONArray teamArray = divisionObject.getJSONArray(TEAM_TAG);
                for (int t = 0; t < teamArray.length(); t++) {
                    JSONObject teamObject = teamArray.getJSONObject(t);
                    Team team = new Team();
                    team.setId(teamObject.getString(ID));
                    team.setName(teamObject.getString(NAME));
                    team.setMarket(teamObject.getString(MARKET));

                    System.out.println(team.getId());

                    division.getTeams().add(team);
                }

                conference.getDivisions().add(division);
            }

            conferences.add(conference);
        }

        return conferences;
    }
}
