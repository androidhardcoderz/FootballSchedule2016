package com.footballschedule2016.logify.writers;

import com.footballschedule2016.logify.objects.LeagueDates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Scott on 2/10/2016.
 */
public class CreateLeagueObjects {

    SimpleDateFormat dateFormatAP = new SimpleDateFormat("MM/dd/yyyy");

    public List<LeagueDates> findLeagueDatesFromStream(InputStream is) throws IOException {

        List<LeagueDates> dates = new ArrayList<LeagueDates>();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is));

        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            LeagueDates lDates = new LeagueDates();
            String [] split = line.split(":");
            lDates.setDate(getCustomDate(split[0]));
            lDates.setInfo(split[1]);

            dates.add(lDates);

        }

        return dates;
    }

    public Date getCustomDate(String dateString){
        try {
            Date date = dateFormatAP.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
