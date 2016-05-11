package com.footballschedule2016.logify;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Scott on 2/28/2016.
 */
public class WeekList {

    private List<String> files = new ArrayList<>();

    public void getWeeklyFiles(Context context){
        try {
            String[] stringFiles = context.getAssets().list("week_files");

            for(int i = 0;i < stringFiles.length;i++){
                getFiles().add(stringFiles[i]);
                Log.i(getClass().getSimpleName(),getFiles().get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(getFiles(),new SortWeekNames());
        System.out.println(getFiles());
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
