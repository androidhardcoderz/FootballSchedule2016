package com.footballschedule2016.logify;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TeamListingFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LinearLayoutManager mLayoutManager;
    private TeamListingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LoadTeamDetails loadTeamDetails;

    public TeamListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hides toolbar from specific fragment
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_listing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TeamListingAdapter(getActivity(), new TeamSelected() {
            @Override
            public void selectedTeam(View view) {
                int i = mRecyclerView.getChildAdapterPosition(view);
                System.out.println("SELECTED TEAM " + mAdapter.getCustomObjectFromPosition(i).getName() + " ");
                showTeamDetailFragment(view, mAdapter.getCustomObjectFromPosition(i));
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * @param view the view selected by user
     * @param teamListing the custom object containing the team information
     */
    private void showTeamDetailFragment(View view,TeamListing teamListing){

        //set index of item selected need it for files searching in detail fragment
        teamListing.setIndex(mAdapter.getIndexOfView(mAdapter.getCustomObjectFromPosition
                (mRecyclerView.getChildAdapterPosition(view))));

        TeamDetailFragment teamDetailFragment = new TeamDetailFragment();

        //create bundle and put parcelable class with team selected
        Bundle bundle = new Bundle();
        bundle.putParcelable("TEAM",teamListing);
        teamDetailFragment.setArguments(bundle);

        //setup scene tranistions if API > 21 LOLLIPOP!

        // Check that the device is running lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Inflate transitions to apply
            Transition changeTransform = TransitionInflater.from(getActivity()).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(getActivity()).
                    inflateTransition(android.R.transition.explode).setDuration(600);

            // Setup exit transition on first fragment
            this.setSharedElementReturnTransition(changeTransform);
            this.setExitTransition(explodeTransform);

            teamDetailFragment.setEnterTransition(explodeTransform);
            teamDetailFragment.setExitTransition(changeTransform);

            // Add second fragment by replacing first
            FragmentTransaction ft = getFragmentManager().beginTransaction()
                    .add(R.id.container, teamDetailFragment)
                    .addToBackStack("detailed");

            // Apply the transaction
            ft.commit();
        }
        else {
            // Code to run on older devices
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container,teamDetailFragment).addToBackStack("detailed").commit();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadTeamDetails = new LoadTeamDetails();
        loadTeamDetails.execute(getActivity());
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
    public void onDestroy() {
        super.onDestroy();
        if(loadTeamDetails != null && loadTeamDetails.getStatus() == AsyncTask.Status.RUNNING){
            loadTeamDetails.cancel(true);
        }
    }

    class LoadTeamDetails extends AsyncTask<Context,TeamListing,Boolean>{

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
        protected Boolean doInBackground(Context... params) {

            //create the team listings objects
            TeamNames teamNames = new TeamNames();
            TeamListingImages teamListingImages = new TeamListingImages();

            for(String string: teamNames.getTeamnames()){
                publishProgress(new TeamListing(string,teamListingImages.getImages().get(teamNames.getTeamnames().indexOf(string))));
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(TeamListing... values) {
            super.onProgressUpdate(values);

            mAdapter.addTeam(values[0]);
            mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
        }
    }



}
