package com.footballschedule2016.logify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class WeeklyGamesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,WeeklyGamesFragment.SetSelectedItemOnNav {

    private WeeklyGamesFragment weeklyGamesFragment;
    private NavigationDrawerMenuInfo navigationDrawerMenuInfo;

    @Override
    public void selectedItemChange(int index) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(navigationDrawerMenuInfo.findIdOfMenuIndex(index));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_games);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        toolbar.setBackgroundColor(Color.parseColor("#FF000000"));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        weeklyGamesFragment = (WeeklyGamesFragment) getSupportFragmentManager().findFragmentById(R.id.weeklyGamesFragment);

        navigationDrawerMenuInfo = new NavigationDrawerMenuInfo(navigationView);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        //interacts with weeklyGamesFragment to change the pager index
        weeklyGamesFragment.changeViewPagerIndex(navigationDrawerMenuInfo.findIndexOfMenuItem(item.getItemId()));

        return true;
    }

}
