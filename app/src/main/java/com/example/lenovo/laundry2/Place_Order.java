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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Place_Order extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int topCount=0;
    int lowerCount=0;
    int bedsheetCount=0;
    int otherCount=0;
    int topIronPrice=0;

    public RadioGroup radioService;
    public RadioButton radiobtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void placeorderSummary1(View view)
    {
        Intent i=new Intent(this,place_order2.class);
        startActivity(i);
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
        getMenuInflater().inflate(R.menu.place__order, menu);
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


    public void topInc(View view)
    {
        if(topCount==12){
            topCount=12;
            Toast.makeText(getApplicationContext(),"Can't be >12",Toast.LENGTH_SHORT).show();
        }
        else {
            topCount = topCount + 1;
            displayTop(topCount);

        }
        updatePrice("top",topCount);
    }

    public void topDec(View view) {
        if (topCount == 0) {
            topCount = 0;
            Toast.makeText(getApplicationContext(), "Cant be <0", Toast.LENGTH_SHORT).show();
        } else {
            topCount = topCount - 1;
            displayTop(topCount);
        }
        updatePrice("top",topCount);
    }

    public void lowerInc(View view)
    {
        if(lowerCount==12){
            lowerCount=12;
            Toast.makeText(getApplicationContext(),"Can't be >12",Toast.LENGTH_SHORT).show();
        }
        else {
            lowerCount = lowerCount + 1;
            displayLower(lowerCount);
        }
        updatePrice("lower",lowerCount);
    }

    public void lowerDec(View view) {
        if (lowerCount == 0) {
            lowerCount = 0;
            Toast.makeText(getApplicationContext(), "Cant be <0", Toast.LENGTH_SHORT).show();
        } else {
            lowerCount = lowerCount - 1;
            displayLower(lowerCount);
        }
        updatePrice("lower",lowerCount);
    }


    public void bedsheetInc(View view)
    {
        if(bedsheetCount==12){
            bedsheetCount=12;
            Toast.makeText(getApplicationContext(),"Can't be >12",Toast.LENGTH_SHORT).show();
        }
        else {
            bedsheetCount = bedsheetCount + 1;
            displayBedsheet(bedsheetCount);
        }
        updatePrice("bedsheet",bedsheetCount);
    }

    public void bedsheetDec(View view) {
        if (bedsheetCount == 0) {
            bedsheetCount = 0;
            Toast.makeText(getApplicationContext(), "Cant be <0", Toast.LENGTH_SHORT).show();
        } else {
            bedsheetCount = bedsheetCount - 1;
            displayBedsheet(bedsheetCount);
        }
        updatePrice("bedsheet",bedsheetCount);
    }

    public void otherInc(View view)
    {
        if(otherCount==12){
            otherCount=12;
            Toast.makeText(getApplicationContext(),"Can't be >12",Toast.LENGTH_SHORT).show();
        }
        else {
            otherCount = otherCount + 1;
            displayOther(otherCount);
        }
        updatePrice("other",otherCount);
    }

    public void otherDec(View view) {
        if (otherCount == 0) {
            otherCount = 0;
            Toast.makeText(getApplicationContext(), "Cant be <0", Toast.LENGTH_SHORT).show();
        } else {
            otherCount = otherCount - 1;
            displayOther(otherCount);
        }
        updatePrice("other",otherCount);
    }




    private void displayTop(int number) {
        TextView topQty = (TextView) findViewById(
                R.id.topQty);
        topQty.setText("" + number);
    }


    private void displayLower(int number) {
        TextView lowerQty = (TextView) findViewById(
                R.id.lowerQty);
        lowerQty.setText("" + number);
    }

    private void displayBedsheet(int number) {
        TextView bedsheetQty = (TextView) findViewById(
                R.id.bedsheetQty);
        bedsheetQty.setText("" + number);
    }

    private void displayOther(int number) {
        TextView otherQty = (TextView) findViewById(
                R.id.otherQty);
        otherQty.setText("" + number);
    }

    private void displayTopPrice(int number) {
        TextView topPrice = (TextView) findViewById(
                R.id.topPrice);
        topPrice.setText("" + number);
    }

    private void displayLowerPrice(int number) {
        TextView lowerPrice = (TextView) findViewById(
                R.id.lowerPrice);
        lowerPrice.setText("" + number);
    }

    private void displayBedsheetPrice(int number) {
        TextView bedsheetPrice = (TextView) findViewById(
                R.id.bedsheetPrice);
        bedsheetPrice.setText("" + number);
    }
    private void displayOtherPrice(int number) {
        TextView otherPrice = (TextView) findViewById(
                R.id.otherPrice);
        otherPrice.setText("" + number);
    }

    public void clickradioButton(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ironRB:
                if (checked)
                {

                    displayTopPrice(topCount*3);
                    displayLowerPrice(lowerCount*3);
                    displayBedsheetPrice(bedsheetCount*3);
                    displayOtherPrice(otherCount*3);
                }
                break;
            case R.id.washRB:
                if (checked)
                {

                    displayTopPrice(topCount * 4);
                    displayLowerPrice(lowerCount*4);
                    displayBedsheetPrice(bedsheetCount*10);
                    displayOtherPrice(otherCount*4);
                }
                break;
            case R.id.bothRB:
                if (checked)
                {

                    displayTopPrice(topCount * 7);
                    displayLowerPrice(lowerCount*7);
                    displayBedsheetPrice(bedsheetCount*12);
                    displayOtherPrice(otherCount*7);
                }
                break;
        }
    }

    public void updatePrice(String clothType, int count)
    {

        radioService = (RadioGroup) findViewById(R.id.radioService);
        int selectedId = radioService.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radiobtn = (RadioButton) findViewById(selectedId);
        if(radiobtn.getText().equals("Wash"))
        {
            if(clothType=="top")
            {

                displayTopPrice(count*4);
            }
            else if(clothType=="lower")
            {
                displayLowerPrice(count*4);
            }
            else if(clothType=="bedsheet")
            {
                displayBedsheetPrice(count*10);
            }
            else if(clothType=="other")
            {
                displayOtherPrice(count*4);
            }
        }
        else if(radiobtn.getText().equals("Iron"))
        {
            if(clothType=="top")
            {

                displayTopPrice(count*3);
            }
            else if(clothType=="lower")
            {
                displayLowerPrice(count*3);
            }
            else if(clothType=="bedsheet")
            {
                displayBedsheetPrice(count*3);
            }
            else if(clothType=="other")
            {
                displayOtherPrice(count*3);
            }
        }
        else if (radiobtn.getText().equals("Wash and Iron"))
        {

            if(clothType=="top")
            {

                displayTopPrice(count*7);
            }
            else if(clothType=="lower")
            {
                displayLowerPrice(count*7);
            }
            else if(clothType=="bedsheet")
            {
                displayBedsheetPrice(count*12);
            }
            else if(clothType=="other")
            {
                displayOtherPrice(count*7);
            }
        }
    }

    //Toast.makeText(this,
    // radiobtn.getText(), Toast.LENGTH_SHORT).show();
}









