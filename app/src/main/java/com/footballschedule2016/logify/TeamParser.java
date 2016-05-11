package com.footballschedule2016.logify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Scott on 2/11/2016.
 */
public class TeamParser {

    /**
     * @param id Team ID To Build upon exmaple: NE = new england patriots
     * @param json the string bufferstream of the standings file on S3 server
     * @return TeamStats object containg name and record
     * @throws JSONException
     */
    public TeamStats getTeamStats(String id,String json) throws JSONException {

        TeamStats teamStats = new TeamStats();

        JSONArray conferenceArray = new JSONObject(json).getJSONArray("conferences");

        for(int i = 0; i < conferenceArray.length();i++){
            //loop through each of the conference arrays in json file
            JSONObject conferenceObject = conferenceArray.getJSONObject(i);

            JSONArray divisionArray = conferenceObject.getJSONArray("divisions");
            for(int d = 0; d < divisionArray.length();d++){

                JSONObject divisionObject = divisionArray.getJSONObject(d);
                Division division = new Division();
                division.setId(divisionObject.getString("id"));
                division.setName(divisionObject.getString("name"));

                JSONArray teamArray = divisionObject.getJSONArray("teams");
                for(int t = 0; t < teamArray.length();t++){
                    JSONObject teamObject = teamArray.getJSONObject(t);

                    if(teamObject.getString("id").equals(id)){
                        teamStats.setDivision(divisionObject.getString("name"));
                        teamStats.setRecord(teamObject.getJSONObject("overall").getString("wins")
                                + "-" + teamObject.getJSONObject("overall").getString("losses"));
                    }
                }
            }
        }

        return teamStats;
    }

}
