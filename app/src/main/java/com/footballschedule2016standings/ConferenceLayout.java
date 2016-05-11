package com.footballschedule2016standings;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.footballschedule2016.logify.R;

/**
 * Created by Scott on 11/23/2015.
 */
public class ConferenceLayout extends LinearLayoutCompat {

    private LinearLayout divisionLinearLayout;

    public ConferenceLayout(Context context,Conference conference) {
        super(context);

        LayoutInflater.from(getContext()).inflate(R.layout.conference_layout, this, true);

        divisionLinearLayout = (LinearLayout) this.findViewById(R.id.divisionLinearLayout);

        for(Division division: conference.getDivisions()){
            divisionLinearLayout.addView(new DivisionLayout(getContext(),division));
        }
    }
}
