package com.footballschedule2016.logify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 3/15/2016.
 */
public class TeamListing implements Parcelable {

    private String name;
    private String image;
    private int index;

    public TeamListing(String name,String image){
        setImage(image);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected TeamListing(Parcel in) {
        name = in.readString();
        image = in.readString();
        index = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeInt(index);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TeamListing> CREATOR = new Parcelable.Creator<TeamListing>() {
        @Override
        public TeamListing createFromParcel(Parcel in) {
            return new TeamListing(in);
        }

        @Override
        public TeamListing[] newArray(int size) {
            return new TeamListing[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
