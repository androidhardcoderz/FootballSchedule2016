package com.footballschedule2016.logify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Scott on 3/15/2016.
 */
public class TeamDetailFragment extends Fragment {

    private TextView teamTextView;
    private ImageView teamImageView;
    private TeamListing teamListing;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TeamRecyclerViewAdapter mAdapter;
    private LoadTeamSchedule loadTeamSchedule;
    private CardView cardView;
    private RelativeLayout navLayout;
    private boolean showing;

    public TeamDetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_detail_fragment,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            teamListing = getArguments().getParcelable("TEAM");
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setOnTouchListener(new View.OnTouchListener() {

            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        teamImageView = (ImageView) view.findViewById(R.id.teamImageView);
        teamTextView = (TextView) view.findViewById(R.id.teamNameTextView);

        Picasso.with(getActivity()).load(teamListing.getImage()).into(teamImageView);
        teamTextView.setText(teamListing.getName());

        if(teamTextView.getText().toString().equals("San Francisco niners"))
            teamTextView.setText("San Francisco 49ers");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.teamDetailRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TeamRecyclerViewAdapter(getActivity());

        // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        loadTeamSchedule = new LoadTeamSchedule();
        loadTeamSchedule.execute(teamListing.getIndex());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(loadTeamSchedule != null && loadTeamSchedule.getStatus() == AsyncTask.Status.RUNNING){
            //kill the BG thread it is assign and attached to fragment only
            loadTeamSchedule.cancel(true);
        }
    }

    class LoadTeamSchedule extends AsyncTask<Integer,Game,Boolean>{

        @Override
        protected void onProgressUpdate(Game... values) {
            super.onProgressUpdate(values);
            mAdapter.addGame(values[0]);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

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
        protected Boolean doInBackground(Integer... params) {
            try {
                TeamFiles teamFiles = new TeamFiles(getActivity());
                ScheduleParser parser = new ScheduleParser();
                parser.parseTeamScheduleFile(new InputStreamConverter(getActivity())
                        .convertInputStreamToString(getActivity().getAssets().open("team_files/"
                                + teamFiles.getFiles().get(params[0]))), new PopGamesToScreen() {
                    @Override
                    public void popGameToScreen(Game game) {
                        publishProgress(game);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


}
