package com.footballschedule2016standings;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Scott on 11/23/2015.
 */
public class SavedData {

    public static final String JSON_DATA = "json_data";

    public static void saveJSONData(Context context,String string){

        PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).edit().putString(JSON_DATA,string).apply();
    }

    public static String getJSONData(Context context){

        return PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).getString(JSON_DATA,"");
    }
}
