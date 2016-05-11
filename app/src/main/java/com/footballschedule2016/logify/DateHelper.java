package com.footballschedule2016.logify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Scott on 2/10/2016.
 */
public class DateHelper {

    SimpleDateFormat parseCaldroid = new SimpleDateFormat("EE MMM dd hh:mm:ss z yyyy");

    public Date convertCaldroidDateToDate(Date date){

        try {
            Date d = parseCaldroid.parse(date +"");
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
