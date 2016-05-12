package com.footballschedule2016.logify.preseason;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footballschedule2016.logify.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 5/11/2016.
 */
public class PreseasonFragment extends Fragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public PreseasonFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preseason_fragment,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //hides toolbar from specific fragment
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);

        this.tabLayout = tabLayout;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        determineWeeksToAdd(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    private void determineWeeksToAdd(ViewPagerAdapter adapter) {
        addWeekFragment(adapter, "Hall Of Fame Game",0);
        addWeekFragment(adapter, "Week One",1);
        addWeekFragment(adapter, "Week Two",2);
        addWeekFragment(adapter, "Week Three",3);
        addWeekFragment(adapter, "Week Four",4);
    }

    private void addWeekFragment(ViewPagerAdapter adapter, String title, int i) {
        PreseasonWeeklyFragment weeklyFragment = new PreseasonWeeklyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("WEEK",i);
        weeklyFragment.setArguments(bundle);
        adapter.addFragment(weeklyFragment, title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.ViewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
