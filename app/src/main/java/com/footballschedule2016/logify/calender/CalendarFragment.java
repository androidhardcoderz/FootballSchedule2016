package com.footballschedule2016.logify.calender;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footballschedule2016.logify.writers.CreateLeagueObjects;
import com.footballschedule2016.logify.helpers.DateHelper;
import com.footballschedule2016.logify.DownloadException;
import com.footballschedule2016.logify.helpers.DownloadURLData;
import com.footballschedule2016.logify.objects.LeagueDates;
import com.footballschedule2016.logify.OnFragmentInteractionListener;
import com.footballschedule2016.logify.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private CaldroidFragment caldroidFragment;
    private GetLeagueDates getLeagueDates;
    private List<LeagueDates> dates;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hides toolbar from specific fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);

        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calContainer, caldroidFragment);
        t.commit();

        caldroidFragment.setCaldroidListener(new OnDateSelected());

        getLeagueDates = new GetLeagueDates();
        getLeagueDates.execute(getActivity());
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    class OnDateSelected extends CaldroidListener {

        /**
         * Inform client user has clicked on a date
         *
         * @param date
         * @param view
         */
        @Override
        public void onSelectDate(Date date, View view) {

            Log.i(getClass().getSimpleName(),"DATE SELECTED " + date);


            if(getLeagueDates.getStatus() == AsyncTask.Status.FINISHED) {
                for (LeagueDates leagueDates : dates) {
                    if (leagueDates.getDate().equals(new DateHelper().convertCaldroidDateToDate(date))) {
                        //date is valid for click event
                        Log.i(getClass().getSimpleName(), "VALID DATE OF " + date);
                        System.out.println(leagueDates.getInfo());
                    }
                }
            }
        }
    }

    class GetLeagueDates extends AsyncTask<Context,Date,List<LeagueDates>>{

        @Override
        protected void onPostExecute(List<LeagueDates> leagueDates) {
            super.onPostExecute(leagueDates);

            dates = leagueDates; //assign field class level variable

            for(LeagueDates lDates: leagueDates){
                caldroidFragment.setSelectedDate(lDates.getDate());
            }

            caldroidFragment.refreshView();
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
        protected List<LeagueDates> doInBackground(Context... params) {

            List<LeagueDates> dates = new ArrayList<>();

            try {
                dates = new CreateLeagueObjects().findLeagueDatesFromStream
                        (new DownloadURLData().downloadData(params[0].getString(R.string.dates_file)));
                return dates;
            } catch (DownloadException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
