package com.footballschedule2016.logify.helpers;

import android.content.Context;
import android.util.Log;

import com.footballschedule2016.logify.PopDateToCalendar;
import com.footballschedule2016.logify.PopGamesToScreen;
import com.footballschedule2016.logify.objects.Game;
import com.footballschedule2016.logify.objects.TeamIDs;
import com.footballschedule2016.logify.objects.Week;
import com.footballschedule2016.logify.writers.WriteToStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Scott on 2/11/2016.
 */
public class ScheduleParser {

    private PopDateToCalendar popDateToCalendar;
    private String json;
    
    public ScheduleParser(PopDateToCalendar popDateToCalendar){
        this.popDateToCalendar = popDateToCalendar;
    }

    public ScheduleParser(String json){
        this.json = json;
    }

    public ScheduleParser(){

    }

    public void createTeamFiles(Context context,String teamName,String id) throws JSONException {

        JSONArray weeksArray = new JSONObject(json).getJSONArray("weeks");
        JSONObject obj = new JSONObject();
        JSONArray arry = new JSONArray();
        System.out.println("CREATING FOR " + id + " " + teamName);

        for(int w = 0; w < weeksArray.length();w++){
            JSONObject weekObj = weeksArray.getJSONObject(w);
            Week week = new Week();
            week.setNumber(weekObj.getString("number"));

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){
                JSONObject gamesObj = gamesArray.getJSONObject(g);

                gamesObj.put("week",week.getNumber());

                if(gamesObj.getString("home").equals(id) || gamesObj.getString("away").equals
                        (id)) {
                    System.out.println("FOUND!!!! " + gamesObj.toString());
                   arry.put(gamesObj);
                }

            }

        }

        obj.put("games",arry);
        new WriteToStorage().writeToFile(context,obj.toString(),teamName);
    }

    public List<Week> parseFullScheduleFile(Context context,String json) throws JSONException {

       Set<String> gameDates = new HashSet<>();

        List<Week> weekGames = new ArrayList<>();

        JSONArray weeksArray = new JSONObject(json).getJSONArray("weeks");

        for(int w = 0; w < weeksArray.length();w++){
            JSONObject weekObj = weeksArray.getJSONObject(w);
            Week week = new Week();
            week.setNumber(weekObj.getString("number"));

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){

                JSONObject gamesObj = gamesArray.getJSONObject(g);

                Game game = new Game();
                game.setScheduled(gamesObj.getString("scheduled"));
                game.setHome(gamesObj.getString("home"));
                game.setVisitor(gamesObj.getString("away"));
                game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                gameDates.add(new FormatGameStartTime().getDateOfGame(game.getScheduled()));
                
            }
        }


       // new WriteToStorage().writeToFile(context,dateFileString,"game_dates.txt");

        return weekGames;
    }

    public List<Week> createDatesJsonFile(Context context,String json) throws JSONException {

        Set<String> gameDates = new HashSet<>();

        List<Week> weekGames = new ArrayList<>();

        JSONArray weeksArray = new JSONObject(json).getJSONArray("weeks");

        for(int w = 0; w < weeksArray.length();w++){
            JSONObject weekObj = weeksArray.getJSONObject(w);
            Week week = new Week();
            week.setNumber(weekObj.getString("number"));

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){
                JSONObject gamesObj = gamesArray.getJSONObject(g);


                Game game = new Game();
                game.setScheduled(gamesObj.getString("scheduled"));
                game.setHome(gamesObj.getString("home"));
                game.setVisitor(gamesObj.getString("away"));
                game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                gameDates.add(new FormatGameStartTime().getDateOfGame(game.getScheduled()));

                week.getGames().add(game);

            }


        }

        List<String> dates = new ArrayList<>();
        dates.addAll(gameDates);
        System.out.println(dates);

        String dateFileString = "";
        for(String string: dates){
            dateFileString += string + "\n";
        }

        new WriteToStorage().writeToFile(context, dateFileString, "game_dates.txt");

        return weekGames;
    }

    public void findGamesByDate(String date, String json, PopGamesToScreen popGamesToScreen) throws JSONException, ParseException {

        JSONArray weeksArray = new JSONObject(json).getJSONArray("dates");

        for(int w = 0; w < weeksArray.length();w++){
            JSONObject weekObj = weeksArray.getJSONObject(w);

            if(weekObj.getString("date").equals(date)) {
                //check if date in array index == the selected date parameter
                Log.i(getClass().getSimpleName(),weekObj.getString("date"));
                JSONArray gamesArray = weekObj.getJSONArray("dates");
                for(int g = 0; g < gamesArray.length();g++){
                    JSONObject gamesObj = gamesArray.getJSONObject(g);

                    //create game object with parameters
                    Game game = new Game();
                    game.setScheduled(gamesObj.getString("scheduled"));
                    game.setHome(TeamIDs.findTeamName(gamesObj.getString("home")));
                    game.setVisitor(TeamIDs.findTeamName(gamesObj.getString("away")));
                    game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));
                    game.setTime(new FormatGameStartTime().getTimeOfGame(game.getScheduled()));
                    game.setDateString(new FormatGameStartTime().getDateOfGame(game.getScheduled()));

                    Log.i(getClass().getSimpleName(), game.getVisitor() + " " + game.getHome());
                    popGamesToScreen.popGameToScreen(game);
                }
            }
        }
    }

    /**
     * @param name Team ID
     * @param json schedule buffer stream
     * @return games list containg all games for the current team
     * @throws JSONException
     */
    public List<Game> parseTeamScheduleFile(String name,String json) throws JSONException {

        List<Game> games = new ArrayList<>();

        JSONArray weeksArray = new JSONObject(json).getJSONArray("weeks");

        for(int w = 0; w < weeksArray.length();w++){
            JSONObject weekObj = weeksArray.getJSONObject(w);
            Week week = new Week();
            week.setNumber(weekObj.getString("number"));

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){
                JSONObject gamesObj = gamesArray.getJSONObject(g);

                if (gamesObj.getString("home").equals(name) || gamesObj.getString("away").equals(name)) {
                    Game game = new Game();
                    game.setScheduled(gamesObj.getString("scheduled"));
                    game.setHome(gamesObj.getString("home"));
                    game.setVisitor(gamesObj.getString("away"));
                    game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                    games.add(game);
                }else{
                    continue;
                }
            }
        }
        return games;
    }

    public void parseTeamScheduleFile(String json,PopGamesToScreen popGamesToScreen) throws JSONException {
        JSONArray gameArray = new JSONObject(json).getJSONArray("games");

        for(int i = 0; i < gameArray.length();i++){

            JSONObject obj = gameArray.getJSONObject(i);

            Game game = new Game();
            game.setScheduled(obj.getString("scheduled"));
            game.setHome(obj.getString("home"));
            game.setVisitor(obj.getString("away"));
            game.setWeek(obj.getString("week"));
            game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));
            game.setDateString(new FormatGameStartTime().getDateOfGame(game.getScheduled()));
            game.setTime(new FormatGameStartTime().getTimeOfGame(game.getScheduled()));
            game.setHome(TeamIDs.findTeamName(game.getHome()));
            game.setVisitor(TeamIDs.findTeamName(game.getVisitor()));

            popGamesToScreen.popGameToScreen(game);
        }
    }

    /**
     * @param jsonString the string containing the entire json schedule file
     * @param date date selected by user on the calender
     * @return a List of games occurring on that specific date
     */
    public List<Game> searchForSpecificDate(String jsonString,Date date) throws JSONException {

        List<Game> games = new ArrayList<>();

        JSONArray datesArray = new JSONObject(jsonString).getJSONArray("dates");

        for(int w = 0; w < datesArray.length();w++){
            JSONObject weekObj = datesArray.getJSONObject(w);

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){
                JSONObject gamesObj = gamesArray.getJSONObject(g);

                if (gamesObj.getString("home").equals("") || gamesObj.getString("away").equals("")) {
                    Game game = new Game();
                    game.setScheduled(gamesObj.getString("scheduled"));
                    game.setHome(gamesObj.getString("home"));
                    game.setVisitor(gamesObj.getString("away"));
                    game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                    games.add(game);
                }else{
                    continue;
                }
            }
        }
        return games;
    }

    public void getWeeklyGames(String json,PopGamesToScreen popGamesToScreen,int number) throws
            JSONException {

        JSONArray weekArray = new JSONObject(json).getJSONArray("weeks");

        for(int i = 0; i < weekArray.length();i++){

            JSONObject objecta = weekArray.getJSONObject(i);
            if(objecta.getString("number").equals(number + "")){
                JSONArray array = objecta.getJSONArray("games");
                for(int a = 0; a < array.length();a++){
                    JSONObject object = array.getJSONObject(a);
                    Game game = new Game();
                    game.setScheduled(object.getString("scheduled"));
                    game.setHome(TeamIDs.findTeamName(object.getString("home")));
                    game.setVisitor(TeamIDs.findTeamName(object.getString("away")));
                    game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));
                    game.setDateString(new FormatGameStartTime().getDateOfGame(game.getScheduled()));
                    game.setTime(new FormatGameStartTime().getTimeOfGame(game.getScheduled()));
                    popGamesToScreen.popGameToScreen(game);
                }

                break;
            }
        }
    }
    public void getWeeklyGames(String json,PopGamesToScreen popGamesToScreen) throws JSONException {

        JSONArray weekArray = new JSONObject(json).getJSONArray("week");

        for(int i = 0; i < weekArray.length();i++){

            JSONObject object = weekArray.getJSONObject(i);

            Game game = new Game();
            game.setScheduled(object.getString("scheduled"));
            game.setHome(TeamIDs.findTeamName(object.getString("home")));
            game.setVisitor(TeamIDs.findTeamName(object.getString("away")));
            game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));
            game.setDateString(new FormatGameStartTime().getDateOfGame(game.getScheduled()));
            game.setTime(new FormatGameStartTime().getTimeOfGame(game.getScheduled()));
            popGamesToScreen.popGameToScreen(game);
        }

    }

    public void createWeeksJsonFile(Context context,String json) throws JSONException {

        JSONArray weeksArray = new JSONObject(json).getJSONArray("weeks");

        for(int w = 0; w < weeksArray.length();w++){

            JSONObject weekObj = weeksArray.getJSONObject(w);
            Week week = new Week();
            week.setNumber(weekObj.getString("number"));

            JSONObject wkObject = new JSONObject();
            wkObject.put("number",week.getNumber());
            JSONArray wkArray = new JSONArray();

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){
                JSONObject gamesObj = gamesArray.getJSONObject(g);
                wkArray.put(gamesObj);
            }

            wkObject.put("week",wkArray);

            System.out.println(wkObject.toString());

           new WriteToStorage().writeToFile(context,wkObject.toString(),"week_" + wkObject.getString("number"));

        }

    }

    public void createScheduleByDatesJsonFile(Context context,String json) throws JSONException, ParseException {

        Set<String> gameDates = new HashSet<>();
        JSONArray weeksArray = new JSONObject(json).getJSONArray("weeks");

        for(int w = 0; w < weeksArray.length();w++){
            JSONObject weekObj = weeksArray.getJSONObject(w);
            Week week = new Week();
            week.setNumber(weekObj.getString("number"));

            JSONArray gamesArray = weekObj.getJSONArray("games");
            for(int g = 0; g < gamesArray.length();g++){
                JSONObject gamesObj = gamesArray.getJSONObject(g);


                Game game = new Game();
                game.setScheduled(gamesObj.getString("scheduled"));
                game.setHome(gamesObj.getString("home"));
                game.setVisitor(gamesObj.getString("away"));
                game.setDate(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                gameDates.add(new FormatGameStartTime().getDateOfGame(game.getScheduled()));

                week.getGames().add(game);

            }


        }

        List<String> dates = new ArrayList<>();
        dates.addAll(gameDates);

        JSONObject datesObject = new JSONObject();
        JSONArray datesArray = new JSONArray();

        for(String string: dates){
            JSONObject object = new JSONObject();
            object.put("date",string.toString());
            JSONArray gameObject = new JSONArray();

            weeksArray = new JSONObject(json).getJSONArray("weeks");

            for(int w = 0; w < weeksArray.length();w++){
                JSONObject weekObj = weeksArray.getJSONObject(w);

                JSONArray gamesArray = weekObj.getJSONArray("games");
                for(int g = 0; g < gamesArray.length();g++){
                    JSONObject gamesObj = gamesArray.getJSONObject(g);

                   if(new FormatGameStartTime().getMMDDYYYYDate(gamesObj.getString("scheduled")).equals(string)){
                       gameObject.put(gamesObj);
                   }
                }
            }
            object.putOpt("dates",gameObject);
            datesArray.put(object);
        }

        datesObject.put("dates",datesArray);


        new WriteToStorage().writeToFile(context, datesObject.toString(), "schedule_by_dates.txt");
    }
}
