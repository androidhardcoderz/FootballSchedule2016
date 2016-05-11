package com.footballschedule2016.logify;

import java.util.Date;

/**
 * Created by Scott on 2/10/2016.
 */
public class LeagueDates {

    private Date date;
    private String info;

    public LeagueDates(Date date, String info){
       setDate(date);
        setInfo(info);
    }

    public LeagueDates(){
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
