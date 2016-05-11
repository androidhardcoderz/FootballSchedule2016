package com.footballschedule2016standings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.footballschedule2016.logify.R;


public class ScrollingActivity extends AppCompatActivity implements DownloadResultReceiver.Receiver,RetryService{

    private final String TAG = ScrollingActivity.class.getSimpleName();
    private DownloadResultReceiver mReceiver;
    private StandingsFragment standingsFragment;
    private final int ALARM_REPEAT = 1080000;

    @Override
    public void onRetry() {
        //retry service download after no internet connection exception
        //shown in snackbar design feature after download failure with specific exception
        //Starting Download Service
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        // Send optional extras to Download IntentService
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        //create broadcast receiver with pending intent to run when user opens app

        /*
        Intent alarm = new Intent(this, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);

        //if current alarm s not running create a new one and set repeating time
        if(alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //set repeating alarm to fire every 6 hours if data is new on server issue notification
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), ALARM_REPEAT * 2, pendingIntent);
        }

        */
        //Starting Download Service
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        // Send optional extras to Download IntentService
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        attachStandingsFragment();
    }

    private void attachStandingsFragment() {
        standingsFragment = new StandingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,standingsFragment).commit();
    }

    /*
        Used to receive messages from Download Service
     */
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                standingsFragment.updateStandings(resultData.getString(Intent.EXTRA_TEXT));
                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                showErrorDialog(error);
                break;
            case DownloadService.STATUS_NO_INTERNET:
                /* Handle the error */
                standingsFragment.showError();
                break;
        }
    }


    private void showErrorDialog(String error){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,
                android.support.v7.appcompat.R.style.AlertDialog_AppCompat);


        // set dialog message
        alertDialogBuilder
                .setTitle("ERROR")
                .setMessage("Error Code: " + error + "\n" + "Make sure you are connected to a network and try again")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        ScrollingActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"ON DESTROY");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"ON PAUSE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"ON RESUME");
    }
}
