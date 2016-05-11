package com.footballschedule2016.logify;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Scott on 2/24/2016.
 */
public class WriteToStorage {

    public void writeToFile(Context context,String data,String fileName) {
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    fileName + ".txt");
            FileOutputStream stream = new FileOutputStream(file);
            System.out.println(file);

            try {
                stream.write(data.getBytes());
                Log.i(getClass().getSimpleName(),fileName + " WAS WRITTEN");
            } finally {
                stream.close();
            }
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
