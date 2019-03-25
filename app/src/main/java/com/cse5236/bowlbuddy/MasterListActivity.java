package com.cse5236.bowlbuddy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MasterListActivity extends AppCompatActivity {
    private final static String TAG = MasterListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        // Manually set action bar, with menu button
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white);

        FragmentManager fm = getSupportFragmentManager();

        // Try to find the Fragment if it's already been created
        Fragment fragment = fm.findFragmentById(R.id.master_list_container);

        if(fragment == null) {
            fragment = new MasterListFragment();
            fm.beginTransaction()
                    .add(R.id.master_list_container, fragment)
                    .commit();
        }
        Log.d(TAG, "onCreate: Successfully created");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If populated in onCreate, returns null object, wait until created in fragment
        DrawerLayout drawerLayout = findViewById(R.id.master_drawer);

        switch(item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Successfully ended activity");
        finish();
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Successfully paused activity");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Successfully resumed activity");
    }

}
