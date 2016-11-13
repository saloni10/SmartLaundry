package com.example.lenovo.laundry2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class History extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView monthtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i=new Intent(this,UserPage.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rateList) {
            Intent i=new Intent(this,RateList.class);
            startActivity(i);
        } else if (id == R.id.nav_ongoingOrders) {
            Intent i=new Intent(this,OngoingOrders.class);
            startActivity(i);
        } else if (id == R.id.nav_monthlyBills) {
            Intent i=new Intent(this,MonthlyBills.class);
            startActivity(i);
        } else if (id == R.id.nav_history) {
            Intent i=new Intent(this,History.class);
            startActivity(i);
        } else if (id == R.id.nav_account) {
            Intent i=new Intent(this,Account.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            Intent i=new Intent(this,Settings.class);
            startActivity(i);
        }
        else if (id == R.id.nav_info) {
            Intent i=new Intent(this,Information.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void prevmonth(View v)
    {

        monthtv=(TextView)findViewById(R.id.monthtv);
        String month=monthtv.getText().toString();
        if(month.equals("February"))
        {
            monthtv.setText("January");
        }

        if(month.equals("March"))
        {
            monthtv.setText("February");
        }
        if(month.equals("April"))
        {
            monthtv.setText("March");
        }
        if(month.equals("May"))
        {
            monthtv.setText("April");
        }
        if(month.equals("June"))
        {
            monthtv.setText("May");
        }
        if(month.equals("July"))
        {
            monthtv.setText("June");
        }
        if(month.equals("August"))
        {
            monthtv.setText("July");
        }
        if(month.equals("September"))
        {
            monthtv.setText("August");
        }
        if(month.equals("October"))
        {
            monthtv.setText("September");
        }
        if(month.equals("November"))
        {
            monthtv.setText("October");
        }
        if(month.equals("December"))
        {
            monthtv.setText("November");
        }

    }

    public void nextmonth(View v) {
        monthtv=(TextView)findViewById(R.id.monthtv);
        String month = monthtv.getText().toString();
        if (month.equals("January")) {
            monthtv.setText("February");
        }

        if (month.equals("February")) {
            monthtv.setText("March");
        }
        if (month.equals("March")) {
            monthtv.setText("April");
        }
        if (month.equals("April")) {
            monthtv.setText("May");
        }
        if (month.equals("May")) {
            monthtv.setText("June");
        }
        if (month.equals("June")) {
            monthtv.setText("July");
        }
        if (month.equals("July")) {
            monthtv.setText("August");
        }
        if (month.equals("August")) {
            monthtv.setText("September");
        }
        if (month.equals("September")) {
            monthtv.setText("October");
        }
        if (month.equals("October")) {
            monthtv.setText("November");
        }
        if (month.equals("November")) {
            monthtv.setText("December");
        }

    }

}


