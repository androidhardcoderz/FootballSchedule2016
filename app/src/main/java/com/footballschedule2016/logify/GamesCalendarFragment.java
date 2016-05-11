package com.footballschedule2016.logify;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GamesCalendarFragment extends Fragment {


    private TextView infoTextView;
    private CaldroidFragment caldroidFragment;
    private GetAllGamesForSeason getGamesForSeason;
    private String jsonString;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecylerViewAdapter mAdapter;

    public GamesCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hides toolbar from specific fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //add caladroid fragment to container hosting this fragment
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.SIX_WEEKS_IN_CALENDAR,1);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.gamesCalContainer, caldroidFragment);
        t.commit();

        caldroidFragment.setCaldroidListener(new CalendarEventListener());

    }

    class CalendarEventListener extends CaldroidListener{

        /**
         * Inform client user has clicked on a date
         *
         * @param date
         * @param view
         */
        @Override
        public void onSelectDate(Date date, View view) {

            //user selected a date check if valid date occurs for the game
            Log.i(getClass().getSimpleName(), "DATE SELECTED " + date);

            if(caldroidFragment.isSelectedDate(date)){
                if(getGamesForSeason != null && getGamesForSeason.getStatus() == AsyncTask.Status.RUNNING){
                    getGamesForSeason.cancel(true);
                }

                mAdapter.clearAll(); //clears all elements in list adapter

                //begins a new BG Async thread that finds the selected game date and parses the
                //games in the schedule_by_dates files /assets
                getGamesForSeason = new GetAllGamesForSeason();
                getGamesForSeason.execute(new FormatGameStartTime().convertDateToString(date));

                if(infoTextView.getVisibility() == View.VISIBLE){
                    infoTextView.setVisibility(View.INVISIBLE);
                    mRecyclerView.bringToFront();
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // creates an item animations sets custom parameters for adding and
        // removing items in the list
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1100);
        defaultItemAnimator.setRemoveDuration(800);
        mRecyclerView.setItemAnimator(defaultItemAnimator);

        // specify an adapter (see also next example)
        mAdapter = new RecylerViewAdapter(mOnClickListener);
        mRecyclerView.setAdapter(mAdapter);

        new PopulateDatesToCal().execute(getActivity());

        infoTextView = (TextView) view.findViewById(R.id.infoTextView);
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(getClass().getSimpleName(),v.getId() + "CLicked");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                setUpExplodeAnimation();
            }

        }
    };

    private void setUpExplodeAnimation(){
        Explode explodeTransition = new Explode();
        //explodeTransition.setDuration(2000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_calendar, container, false);
    }

    /**
     * background thread class
     * send in the current activity context as param
     * context is used to open asset txt file containing dates in MM/dd/yyyy format
     * dates are parsed from the file in .inputstreamconverter class
     * then publuished on the UI thread using onPublishProgress to add the date
     * to the calandroidFragment and refresh its view showing the date highlighted
     *
     */
    class PopulateDatesToCal extends AsyncTask<Context,Date,String>{

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            jsonString = aVoid;
        }

        @Override
        protected void onProgressUpdate(Date... values) {
            super.onProgressUpdate(values);

            if(!caldroidFragment.isSelectedDate(values[0])){
                caldroidFragment.setSelectedDate(values[0]);
                caldroidFragment.refreshView();
            }
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(Context... params) {

            try {
                new InputStreamConverter(params[0]).getAllGameDatesFromAssets
                        (params[0].getAssets().open("game_dates.txt"), new PopGamesToCal() {
                    @Override
                    public void onDateFound(Date date) {
                        publishProgress(date);
                    }
                });

                return new InputStreamConverter(params[0])
                        .convertInputStreamToString(params[0].getAssets().open("schedule_by_dates.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class GetAllGamesForSeason extends AsyncTask<String,Game,List<Game>>{

        @Override
        protected void onProgressUpdate(Game... values) {
            super.onProgressUpdate(values);

            Log.i(getClass().getSimpleName(), "ADDING GAME");
            mAdapter.addItem(values[0]);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Game> doInBackground(String... params) {

            PopGamesToScreen popGamesToScreen = new PopGamesToScreen() {
                @Override
                public void popGameToScreen(Game game) {
                    publishProgress(game);

                    //sleep thread to show adding animations!
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                Log.i(getClass().getSimpleName(),params[0]);
                new ScheduleParser().findGamesByDate(params[0], jsonString, popGamesToScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
