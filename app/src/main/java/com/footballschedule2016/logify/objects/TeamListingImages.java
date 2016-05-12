package com.footballschedule2016.logify.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 3/15/2016.
 */
public class TeamListingImages {
    
    private List<String> images = new ArrayList<>();

    /**
     * constructor
     * generates the collections containg http REST url calls for each team
     */
    public TeamListingImages(){

        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/cardinals.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/falcons.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/ravens.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/bills.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/panthers.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/bears.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/bengals.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/browns.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/cowboys.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/broncos.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/lions.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/packers.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/texans.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/colts.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/jaguars.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/chiefs.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/rams.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/dolphins.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/vikings.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/patriots.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/saints.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/giants.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/jets.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/raiders.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/eagles.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/steelers.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/chargers.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/niners.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/seahawks.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/bucs.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/titans.png");
        images.add("https://s3.amazonaws.com/com.nflschedule2016.scottauman/team_color_images/redskins.png");
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
