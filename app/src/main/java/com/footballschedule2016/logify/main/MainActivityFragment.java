package com.footballschedule2016.logify.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.footballschedule2016.logify.OnFragmentInteractionListener;
import com.footballschedule2016.logify.R;
import com.footballschedule2016.logify.views.TeamListingFragment;
import com.footballschedule2016.logify.views.WeeklyGamesActivity;
import com.footballschedule2016.logify.calender.GamesCalendarFragment;
import com.footballschedule2016.logify.preseason.PreseasonFragment;
import com.footballschedule2016standings.ScrollingActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @Bind(R.id.teamsButton)
    Button teamsButton;
    @Bind(R.id.gameCalendarButton)
    Button gameCalendarButton;
    @Bind(R.id.standingsButton)
    Button standingsButton;
    @Bind(R.id.weeksButton)
    Button weeklyButton;
    @Bind(R.id.preseasonButton)
    Button preseasnButton;
    private OnFragmentInteractionListener mListener;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        teamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChangeFragmentCondition(new TeamListingFragment());
            }
        });

        gameCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChangeFragmentCondition(new GamesCalendarFragment());
            }
        });

        standingsButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                mListener.onNewActivity(new Intent(getActivity(), ScrollingActivity.class));
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNewActivity(new Intent(getActivity(), WeeklyGamesActivity.class));
            }
        });
        if (checkPreseasonGamesDate()) {
            preseasnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onChangeFragmentCondition(new PreseasonFragment());
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //hides toolbar from specific fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);
    }

    private boolean checkPreseasonGamesDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar lastGames = Calendar.getInstance();
        Calendar nowGames = Calendar.getInstance();
        try {
            lastGames.setTime(dateFormat.parse("09/02/2016"));
            nowGames.setTime(new Date());
            if(lastGames.after(nowGames)) {
                preseasnButton.setVisibility(View.VISIBLE);
                return true;
            }else{
                preseasnButton.setVisibility(View.GONE);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    private void showInfoDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Coming Soon")
                .setMessage("Schedule is to be released in April 2016, check back after the " +
                        "release of schedule")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AppCompatDialog alert = builder.create();
        alert.show();
    }

    public void startNewActivity(Context context, String packageName) {

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
