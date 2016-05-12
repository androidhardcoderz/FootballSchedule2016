package com.footballschedule2016.logify.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.footballschedule2016.logify.R;
import com.footballschedule2016.logify.objects.Game;

/**
 * Created by Scott on 2/29/2016.
 */
public class GameLayout extends LinearLayout {

    private TextView visitor,home,date,time;

    public GameLayout(Context context,Game game) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.game_layout, this, true);

        visitor = (TextView) this.findViewById(R.id.visitorTeamNameTextView);
        home = (TextView) this.findViewById(R.id.homeTeamNameTextView);
        date = (TextView) this.findViewById(R.id.dateGameTextView);
        time = (TextView) this.findViewById(R.id.timeGameTextView);

        visitor.setText(game.getVisitor());
        home.setText(game.getHome());
        date.setText(game.getDateString());
        time.setText(game.getTime());

    }

    public GameLayout(Context context,Game game,int padding) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.game_layout, this, true);

        this.setPadding(padding,padding,padding,padding);
        visitor = (TextView) this.findViewById(R.id.visitorTeamNameTextView);
        home = (TextView) this.findViewById(R.id.homeTeamNameTextView);
        date = (TextView) this.findViewById(R.id.dateGameTextView);
        time = (TextView) this.findViewById(R.id.timeGameTextView);

        visitor.setText(game.getVisitor());
        home.setText(game.getHome());
        date.setText(game.getDateString());
        time.setText(game.getTime());

    }

    public GameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
