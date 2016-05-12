package com.footballschedule2016.logify.views;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.footballschedule2016.logify.PopGamesToScreen;
import com.footballschedule2016.logify.R;
import com.footballschedule2016.logify.helpers.InputStreamConverter;
import com.footballschedule2016.logify.helpers.ScheduleParser;
import com.footballschedule2016.logify.objects.Game;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Scott on 3/17/2016.
 */
public class WeeklyFragment extends Fragment {

    private WeeklyGameLoader weeklyGameLoader;
    private ProgressBar pBar;
    private LinearLayout gamesLayout;
    private final String FILENAME = "filename";

    public WeeklyFragment(){

    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if(weeklyGameLoader != null && weeklyGameLoader.getStatus() == AsyncTask.Status.RUNNING){
            weeklyGameLoader.cancel(true);
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weekly_fragment,container,false);

        pBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        gamesLayout = (LinearLayout) view.findViewById(R.id.gamesLayout);

        return view;
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weeklyGameLoader = new WeeklyGameLoader(getActivity());
        weeklyGameLoader.execute(getArguments().getString(FILENAME));
    }

    class WeeklyGameLoader extends AsyncTask<String,Game,Void>{

        private Context context;

        public WeeklyGameLoader(Context context){
            this.context = context;
        }

        @Override
        protected void onProgressUpdate(Game... values) {
            super.onProgressUpdate(values);

            Log.i(getClass().getSimpleName(), "GAME");
            System.out.println(values[0].getDate() + values[0].getTime() + values[0].getVisitor() + values[0].getHome());
            gamesLayout.addView(new GameLayout(context,values[0],24));

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
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
        protected Void doInBackground(String... params) {

            PopGamesToScreen popGamesToScreen = new PopGamesToScreen() {
                @Override
                public void popGameToScreen(Game game) {
                    publishProgress(game);
                }
            };

            ScheduleParser scheduleParser = new ScheduleParser();
            try {
                scheduleParser.getWeeklyGames(new InputStreamConverter(context).convertInputStreamToString(
                        context.getAssets().open("week_files/" + params[0])),popGamesToScreen);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pBar.setVisibility(View.INVISIBLE);
        }
    }
}
