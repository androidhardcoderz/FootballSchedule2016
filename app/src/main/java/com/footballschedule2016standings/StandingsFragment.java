package com.footballschedule2016standings;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.footballschedule2016.logify.R;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Scott on 11/23/2015.
 */
public class StandingsFragment extends Fragment {

    private StandingsLoaderTask standingsLoaderTask;
    private LinearLayout standingsLinearLayout;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private RetryService callback;

    public StandingsFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (RetryService) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RetryService");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(standingsLoaderTask != null && standingsLoaderTask.getStatus() == AsyncTask.Status.RUNNING){
            standingsLoaderTask.cancel(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.standings_fragment,container,false);

        standingsLinearLayout = (LinearLayout) view.findViewById(R.id.standingsLinearLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.standingsCoordinatorLayout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //start service uing callback interface
        callback.onRetry();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);
    }

    public void updateStandings(String jsonString){
        standingsLoaderTask = new StandingsLoaderTask();
        standingsLoaderTask.execute(jsonString);
    }

    public void showError(){
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onRetry();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    class StandingsLoaderTask extends AsyncTask<String,Conference,List<Conference>>{

        ConferenceLoaded conferenceLoaded = new ConferenceLoaded() {
            @Override
            public void onConferenceLoaded(Conference conference) {
                publishProgress(conference);
            }
        };

        public StandingsLoaderTask(){

        }

        @Override
        protected void onProgressUpdate(Conference... values) {
            super.onProgressUpdate(values);
            standingsLinearLayout.addView(new ConferenceLayout(getActivity(), values[0]));
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
        protected List<Conference> doInBackground(String... params) {

            ParseJSONFile parseJSONFile = new ParseJSONFile(getActivity(),conferenceLoaded);
            try {
                return parseJSONFile.parseStandingsJsonString(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Conference> conferences) {
            super.onPostExecute(conferences);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
}
