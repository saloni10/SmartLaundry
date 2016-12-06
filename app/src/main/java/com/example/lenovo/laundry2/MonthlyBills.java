package com.example.lenovo.laundry2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MonthlyBills extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ValueEventListener morderListener;
    private DatabaseReference morder;
    NavigationView navigationView;
    String userid;
    TextView textname;
    TextView text;
    String n;
    TextView janbill,febbill,marbill,aprbill,maybill,junebill,julybill,augbill,septbill,octbill,novbill,decbill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_bills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.email_nav_monthly);
        textname = (TextView) header.findViewById(R.id.name_nav_monthly);
        janbill=(TextView) findViewById(R.id.janbill);
        febbill=(TextView) findViewById(R.id.febbill);
        marbill=(TextView) findViewById(R.id.marbill);
        aprbill=(TextView) findViewById(R.id.aprilbill);
        maybill=(TextView) findViewById(R.id.maybill);
        junebill=(TextView) findViewById(R.id.junebill);
        julybill=(TextView) findViewById(R.id.julybill);
        augbill=(TextView) findViewById(R.id.augbill);
        septbill=(TextView) findViewById(R.id.septbill);
        octbill=(TextView) findViewById(R.id.octbill);
        novbill=(TextView) findViewById(R.id.novbill);
        decbill=(TextView) findViewById(R.id.decbill);



        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        morder = FirebaseDatabase.getInstance().getReference().child("Orders");

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent i = new Intent(MonthlyBills.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        };




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }



    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        ValueEventListener userlistener= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                text.setText(ud.getEmail());
                textname.setText(ud.getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mdb.addValueEventListener(userlistener);


        Name();
        ValueEventListener orderListener = new ValueEventListener() {

            final int month[]=new int[12];

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // User user = dataSnapshot.getValue(User.class);
                for(DataSnapshot dsp: dataSnapshot.getChildren())
                {

                    OrderItems order= dsp.getValue(OrderItems.class);
                    userid=mAuth.getCurrentUser().getUid();
                    if( order.getUsername().equals(n) ) {
                        month[order.getMonth()-1]=month[order.getMonth()-1]+order.getTotalPrice();


                    }

                }
                janbill.setText(String.valueOf(month[0]));
                febbill.setText(String.valueOf(month[1]));
                marbill.setText(String.valueOf(month[2]));
                aprbill.setText(String.valueOf(month[3]));
                maybill.setText(String.valueOf(month[4]));
                junebill.setText(String.valueOf(month[5]));
                julybill.setText(String.valueOf(month[6]));
                augbill.setText(String.valueOf(month[7]));
                septbill.setText(String.valueOf(month[8]));
                octbill.setText(String.valueOf(month[9]));
                novbill.setText(String.valueOf(month[10]));
                decbill.setText(String.valueOf(month[11]));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        morder.addListenerForSingleValueEvent(orderListener);
        morderListener = orderListener;





    }



    public void Name()
    {

        ValueEventListener user= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                n=ud.getName();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mdb.addValueEventListener(user);

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
        getMenuInflater().inflate(R.menu.monthly_bills, menu);
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
        if (id == R.id.action_logout) {
            mAuth.signOut();
            Intent i=new Intent(MonthlyBills.this,LoginActivity.class);
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
        } else if (id == R.id.nav_history) {
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
        else if (id == R.id.nav_monthlyBills) {
            Intent i = new Intent(this, MonthlyBills.class);
            startActivity(i);
        }
        else if (id == R.id.notifications) {
            Intent i=new Intent(this,OrderNotifications.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

