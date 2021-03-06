package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

    private static String LOG_TAG = MainActivity.class.getSimpleName();
    private String mLocation;
    private final String FORECASTFRAGMENT_TAG = "ForacseFragmentTag";

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "Started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(LOG_TAG, "Destroyed");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Utility.getPreferredLocation(this) != mLocation) {
            ForecastFragment frag = (ForecastFragment) getSupportFragmentManager()
                    .findFragmentByTag(FORECASTFRAGMENT_TAG);

            // Calls a fragment method that updates the weather and puts it to the db
            frag.onLocationChange();

            // Updateing the current location
            mLocation = Utility.getPreferredLocation(this);
        }

        Log.d(LOG_TAG, "Resumed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment(), FORECASTFRAGMENT_TAG)
                    .commit();
        }

        mLocation = Utility.getPreferredLocation(this);

        Log.d(LOG_TAG, "Created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_show_location) {
            showMap(Uri.parse("geo:0,0").buildUpon()
                        .appendQueryParameter("q", Utility.getPreferredLocation(this))
                        .build());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(LOG_TAG, "Stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Paused");
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
