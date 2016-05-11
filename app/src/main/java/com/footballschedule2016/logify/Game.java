package com.footballschedule2016.logify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
public class Game implements Parcelable {

    private String time;
    private Date date;
    private String place;
    private String visitor;
    private String home;
    private String tv;
    private String dateString;
    private boolean primetime;
    private String scheduled;
    private String week;

    public Game() {

    }


    protected Game(Parcel in) {
        time = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        place = in.readString();
        visitor = in.readString();
        home = in.readString();
        tv = in.readString();
        dateString = in.readString();
        primetime = in.readByte() != 0x00;
        scheduled = in.readString();
        week = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeString(place);
        dest.writeString(visitor);
        dest.writeString(home);
        dest.writeString(tv);
        dest.writeString(dateString);
        dest.writeByte((byte) (primetime ? 0x01 : 0x00));
        dest.writeString(scheduled);
        dest.writeString(week);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public static Creator<Game> getCREATOR() {
        return CREATOR;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isPrimetime() {
        return primetime;
    }

    public void setPrimetime(boolean primetime) {
        this.primetime = primetime;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}