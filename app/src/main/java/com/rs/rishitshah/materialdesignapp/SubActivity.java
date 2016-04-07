package com.rs.rishitshah.materialdesignapp;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SubActivity extends AppCompatActivity {


    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    NavigationDrawerFragment navigationDrawerFragment;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //needed step as you cannot call setDisplayHomeAsUpEnabled on a null reference.
        // DO not use getActionBar when using AppCompatActivity. It returns a null reference
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                navigationDrawerFragment = (NavigationDrawerFragment)
                        getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);
                navigationDrawerFragment.setUp(R.id.nav_drawer_fragment, mDrawerLayout, toolbar);

                //required to make fragment transactions
                //make sure imports are same as that of the fragment to be added
                //this means if you are importing support library in fragments make
                // sure to import fragmentManger and fragmentTransaction from same suppotrt lib
//                NavigationDrawerFragment df = new NavigationDrawerFragment();
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.drawer_layout, df);
//                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        //this statement is default behaviour when the item is not processed
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
