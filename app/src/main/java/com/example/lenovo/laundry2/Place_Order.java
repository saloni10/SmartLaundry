package com.example.lenovo.laundry2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Place_Order extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth.AuthStateListener mAuthListener;
    NavigationView navigationView;
    String userid;
    String NAME,HOSTEL,ROOM;

    int topCount=0;
    int lowerCount=0;
    int bedsheetCount=0;
    int otherCount=0;
    public RadioGroup radioService;
    public RadioButton radiobtn;
    DatePicker dp;

    TextView topQty,lowerQty,bedsheetQty,otherQty,topPrice,lowerPrice,bedsheetPrice,otherPrice;

    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private DatabaseReference morder;
    TextView textName;
    TextView text;
    TimePicker tp;

    private SecureRandom random=new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         topQty = (TextView) findViewById(R.id.topQty);
         lowerQty = (TextView) findViewById(R.id.lowerQty);
         bedsheetQty = (TextView) findViewById(R.id.bedsheetQty);
         otherQty = (TextView) findViewById(R.id.otherQty);
         topPrice = (TextView) findViewById(R.id.topPrice);
         lowerPrice = (TextView) findViewById(R.id.lowerPrice);
         bedsheetPrice = (TextView) findViewById(R.id.bedsheetPrice);
         otherPrice = (TextView) findViewById(R.id.otherPrice);
        radioService = (RadioGroup) findViewById(R.id.radioService);
        dp=(DatePicker)findViewById(R.id.date_picker) ;
        tp=(TimePicker)findViewById(R.id.time_picker);


        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.email_nav_place_order);
        textName = (TextView) header.findViewById(R.id.name_nav_place_order);


        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        morder= FirebaseDatabase.getInstance().getReference().child("Orders");

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent i = new Intent(Place_Order.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        };

        //Establish connection with network
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isConnected()) {
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_LONG).show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //ValueEventListener for getting user details
        ValueEventListener userlistener= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                text.setText(ud.getEmail());
                textName.setText(ud.getName());

                NAME=ud.getName();
                HOSTEL=ud.getHostel();
                ROOM=ud.getRoom();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mdb.addValueEventListener(userlistener);

    }

    //pass intent to placeorder2 Activity that confirms order.
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
            //placeOrder_database(Place_Order.this);
        }
        if (id == R.id.action_logout) {
            mAuth.signOut();
            Intent i=new Intent(Place_Order.this,LoginActivity.class);
            startActivity(i);

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
        }  else if (id == R.id.nav_history) {
            Intent i=new Intent(this,History.class);
            startActivity(i);
        } else if (id == R.id.nav_account) {
            Intent i=new Intent(this,Account.class);
            startActivity(i);
        }
        else if (id == R.id.nav_info) {
            Intent i=new Intent(this,Information.class);
            startActivity(i);
        }
        else if (id == R.id.notifications) {
            Intent i=new Intent(this,OrderNotifications.class);
            startActivity(i);
        }
        else if (id == R.id.nav_monthlyBills) {
            Intent i = new Intent(this, MonthlyBills.class);
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

        topQty.setText("" + number);
    }


    private void displayLower(int number) {
        lowerQty.setText("" + number);
    }

    private void displayBedsheet(int number) {
        bedsheetQty.setText("" + number);
    }

    private void displayOther(int number) {
        otherQty.setText("" + number);
    }

    private void displayTopPrice(int number) {
        topPrice.setText("" + number);
    }

    private void displayLowerPrice(int number) {

        lowerPrice.setText("" + number);
    }

    private void displayBedsheetPrice(int number) {

        bedsheetPrice.setText("" + number);
    }
    private void displayOtherPrice(int number) {

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



    public void placeOrder_database(View v)
    {

        final int topQ=Integer.parseInt(topQty.getText().toString());
        final int lowerQ=Integer.parseInt(lowerQty.getText().toString());
        final int bedsheetQ=Integer.parseInt(bedsheetQty.getText().toString());
        final int otherQ=Integer.parseInt(otherQty.getText().toString());
        final int topP=Integer.parseInt(topPrice.getText().toString());
        final int lowerP=Integer.parseInt(lowerPrice.getText().toString());
        final int bedsheetP=Integer.parseInt(bedsheetPrice.getText().toString());
        final int otherP=Integer.parseInt(otherPrice.getText().toString());
        int total=topQ+lowerQ+bedsheetQ+otherQ;
        String totalQ=String.valueOf(total);
        int totalp=topP+lowerP+bedsheetP+otherP;
        String totalP=String.valueOf(totalp);

        final String date1 = dp.getDayOfMonth() + "/" + (dp.getMonth() + 1) + "/" + dp.getYear();

        final String time1=tp.getCurrentHour() + ":" + tp.getCurrentMinute();
        if(total==0)
        {
            Toast.makeText(Place_Order.this,"Select valid number of items!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int selectedId = radioService.getCheckedRadioButtonId();
            radiobtn = (RadioButton) findViewById(selectedId);

            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            Date todayDate = new Date();
            final String thisDate = currentDate.format(todayDate);


            final String iron_wash=radiobtn.getText().toString();

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Order Summary");
            LayoutInflater inflater=this.getLayoutInflater();
            View dialogView=inflater.inflate(R.layout.activity_place_order2,null);
            dialog.setView(dialogView);
            TextView topNO=(TextView)dialogView.findViewById(R.id.topNo);
            TextView lowerNO=(TextView)dialogView.findViewById(R.id.lowerNo);
            TextView bedsheetNO=(TextView)dialogView.findViewById(R.id.bedsheetNo);
            TextView otherNO=(TextView)dialogView.findViewById(R.id.otherNo);
            TextView totalNO=(TextView)dialogView.findViewById(R.id.totalNo);
            TextView totalPrice=(TextView)dialogView.findViewById(R.id.totalPrice);
            topNO.setText(topQty.getText().toString());
            lowerNO.setText(lowerQty.getText().toString());
            bedsheetNO.setText(bedsheetQty.getText().toString());
            otherNO.setText(otherQty.getText().toString());
            totalNO.setText(totalQ);
            totalPrice.setText(totalP);
           final String n1=NAME;
            final String h1=HOSTEL;
            final String r1=ROOM;
            dialog.setPositiveButton("Confirm Order", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String orderid= nextOrderID();
                    String userid=mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUser=morder.child(orderid);
                    currentUser.child("OrderID").setValue(orderid);
                    currentUser.child("UserId").setValue(userid);
                    currentUser.child("Shirts").setValue(topQ);
                    currentUser.child("Bedsheets").setValue(bedsheetQ);
                    currentUser.child("Lowers").setValue(lowerQ);
                    currentUser.child("Others").setValue(otherQ);
                    currentUser.child("TypeOfOrders").setValue(iron_wash);
                    currentUser.child("TotalQTY").setValue(topQ+bedsheetQ+lowerQ+otherQ);
                    currentUser.child("TotalPrice").setValue(topP+lowerP+bedsheetP+otherP);
                    currentUser.child("WhenPlaced").setValue(thisDate);
                    currentUser.child("WhenConfirmed").setValue("#1");
                    currentUser.child("WhenCompleted").setValue("#2");
                    currentUser.child("PickupTime").setValue(time1);
                    currentUser.child("PickupDate").setValue(date1);
                    currentUser.child("Username").setValue(n1);
                    currentUser.child("Hostel").setValue(h1);
                    currentUser.child("Room").setValue(r1);
                    currentUser.child("Month").setValue((dp.getMonth()+1));

                    Toast.makeText(Place_Order.this,"Order Placed",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Place_Order.this,UserPage.class);
                    startActivity(i);
                }
            });
            //dialog.setNavigateButton("Cancel", null);
            dialog.create();
            dialog.show();

        }

    }


    public String nextOrderID()
    {
        return new  BigInteger(130,random).toString(32);
    }
}









