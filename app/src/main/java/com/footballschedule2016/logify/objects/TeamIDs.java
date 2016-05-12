package com.footballschedule2016.logify.objects;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2/11/2016.
 */
public class TeamIDs {

    public static List<IDs> ids = new ArrayList<>();

    public TeamIDs(Context context) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(context.getAssets().open("league_ids.txt")));

        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            String[] lines = line.split(",");
            ids.add(new IDs(lines[0],lines[1]));
        }

        for(IDs id: ids){
            System.out.println(id.getName());
        }
    }

    /**
     * searches the IDS listing for the id parameter
     * returns the full team name associated with the team id
     * @param id the team ID
     * @return the team name
     */
    public static String findTeamName(String id){
        for(IDs ides: ids){
            if(ides.getId().equals(id)){
                return ides.getName();
            }
        }

        return ""; //return empty name
    }

    //ID holder class object that holds an id and binds the team name with the ID
    public class IDs{
        private String id;
        private String name;

        public IDs(String id, String name){
            setId(id);
            setName(name);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
