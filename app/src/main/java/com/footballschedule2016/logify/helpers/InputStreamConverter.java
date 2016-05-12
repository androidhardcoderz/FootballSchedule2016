package com.footballschedule2016.logify.helpers;

import android.content.Context;

import com.footballschedule2016.logify.PopGamesToCal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Scott on 11/23/2015.
 */
public class InputStreamConverter {

    private Context context;

    public InputStreamConverter(Context context){
       setContext(context);
    }

    /**
     * @param is inputstream containing text characters
     *           can be textfile json file xml file
     * @return full string containing all text characters
     * @throws IOException file not found
     */
    public String convertInputStreamToString(InputStream is)
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        return result;
    }

    /**
     * @param is inputstream read from games_dates.txt /assets file
     *         contains all game dates used to populate the calendar
     * @param popGamesToCal interface to send back date for each line in the file
     * @throws IOException file not found /assets
     */
    public void getAllGameDatesFromAssets(InputStream is,PopGamesToCal popGamesToCal)
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is));
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            popGamesToCal.onDateFound(new FormatGameStartTime().convertStringToDate(line));
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
