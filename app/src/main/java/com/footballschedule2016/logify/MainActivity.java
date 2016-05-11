package com.footballschedule2016.logify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        try {
            new TeamIDs(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainActivityFragment = new MainActivityFragment();
        //setup mainFragment
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_out, android.R.anim.fade_out);
        trans.replace(R.id.container,mainActivityFragment).commit();
    }

    private void changeFragmentsWithTransistion(Fragment fragment){

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,
                android.R.anim.fade_in,android.R.anim.fade_out);
        trans.replace(R.id.container,fragment).addToBackStack("this").commit();
    }


    /**
     * Used to update fragment back to home if user pressed back
     * in any fragment beyond the home fragment(MainActivityFragment)
     *
     * //kills all fragments beyond the backstack
     //our home fragment (MainActivityFragment) is our home fragment show this fragment when user presses back
     //with a non null backstack, if user pressed back with null backstack application will call onDestroy()
     */
    @Override
    public void onFragmentInteraction() {
        getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * called when suer clicks a button on the home fragment
     * @param fragment to be replaced in the current transaction with manager
     */
    @Override
    public void onChangeFragmentCondition(Fragment fragment) {
        changeFragmentsWithTransistion(fragment);
    }

    @Override
    public void onNewActivity(Intent intent) {
        startActivity(intent);
    }
}
