package com.footballschedule2016.logify.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footballschedule2016.logify.R;
import com.footballschedule2016.logify.helpers.DepthPageTransformer;
import com.footballschedule2016.logify.objects.WeekList;

/**
 * Created by Scott on 2/27/2016.
 */
public class WeeklyGamesFragment extends Fragment  {

    private ViewPager viewPager;
    private WeekList weekList;
    private SetSelectedItemOnNav setSelectedItemOnNav;
    private final String FILENAME = "filename";

    public interface SetSelectedItemOnNav {
        void selectedItemChange(int index);
    }

    /**
     * Default constructor.  <strong>Every</strong> fragment must have an
     * empty constructor, so it can be instantiated when restoring its
     * activity's state.  It is strongly recommended that subclasses do not
     * have other constructors with parameters, since these constructors
     * will not be called when the fragment is re-instantiated; instead,
     * arguments can be supplied by the caller with {@link #setArguments}
     * and later retrieved by the Fragment with {@link #getArguments}.
     * <p/>
     * <p>Applications should generally not implement a constructor.  The
     * first place application code an run where the fragment is ready to
     * be used is in {@linkonAttach(Activity)}, the point where the fragment
     * is actually associated with its activity.  Some applications may also
     * want to implement {@link #onInflate} to retrieve attributes from a
     * layout resource, though should take care here because this happens for
     * the fragment is attached to its activity.
     */
    public WeeklyGamesFragment() {
        super();
    }

    /**
     * Changes the current viewed pager in viewpager
     * linked with navigation drawer
     * @param index the index of menu item selected
     */
    public void changeViewPagerIndex(int index){
        if(viewPager != null){
            viewPager.setCurrentItem(index,true);
        }
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weekly_games_fragment,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            setSelectedItemOnNav = (SetSelectedItemOnNav) context;
        }catch(ClassCastException cce){
            cce.printStackTrace();
        }
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     *  and before
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //initiate the listing of all files needed for this fragment class
        //files are in /assets/week_files
        weekList = new WeekList();
        weekList.getWeeklyFiles(getActivity());

        System.out.println(weekList.getFiles().size() + "");
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to  of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectedItemOnNav.selectedItemChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //sets custom page transformer on the view
        viewPager.setPageTransformer(true,new DepthPageTransformer());

        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FILENAME, weekList.getFiles().get(position));
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                weeklyFragment.setArguments(bundle);
                return weeklyFragment;
            }

            @Override
            public int getCount() {
                return weekList.getFiles().size();
            }

            /**
             * This method may be called by the ViewPager to obtain a title string
             * to describe the specified page. This method may return null
             * indicating no title for this page. The default implementation returns
             * null.
             *
             * @param position The position of the title requested
             * @return A title for the requested page
             */
            @Override
            public CharSequence getPageTitle(int position) {
                return "Week " + (position + 1);
            }

            /**
             * Called when the host view is attempting to determine if an item's position
             * has changed. Returns {@link #POSITION_UNCHANGED} if the position of the given
             * item has not changed or {@link #POSITION_NONE} if the item is no longer present
             * in the adapter.
             * <p/>
             * <p>The default implementation assumes that items will never
             * change position and always returns {@link #POSITION_UNCHANGED}.
             *
             * @param object Object representing an item, previously returned by a call to
             *               {@link #instantiateItem(View, int)}.
             * @return object's new position index from [0, {@link #getCount()}),
             * {@link #POSITION_UNCHANGED} if the object's position has not changed,
             * or {@link #POSITION_NONE} if the item is no longer present.
             */
            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }
        });

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to  of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
