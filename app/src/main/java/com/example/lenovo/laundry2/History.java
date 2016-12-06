package com.example.lenovo.laundry2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

public class History extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView monthtv;
    String userid;
    TextView textname;
    TextView textmail;
    String n;

    private ValueEventListener morderListener;
    private DatabaseReference morder;
    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView View_Recycler;
    public  Ongoing_Adapter adap;


    ArrayList<OrderItems> orderlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        morder = FirebaseDatabase.getInstance().getReference().child("Orders");

        textname = (TextView) header.findViewById(R.id.name_nav_hist);
        textmail = (TextView) header.findViewById(R.id.email_nav_hist);

        View_Recycler=(RecyclerView)findViewById(R.id.rec_list_ongoing);
        View_Recycler.setLayoutManager(new LinearLayoutManager(this));
        Name();


    // valueEventListener - gets the list of all previous orders of the user.
        ValueEventListener orderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // User user = dataSnapshot.getValue(User.class);
                for(DataSnapshot dsp: dataSnapshot.getChildren())
                {

                    OrderItems order= dsp.getValue(OrderItems.class);
                    userid=mAuth.getCurrentUser().getUid();
                    if( order.getUsername().equals(n)) {
                        orderlist.add(order);
                    }

                }
                if(orderlist.size()==0)
                {
                    Toast.makeText(getApplicationContext(), "No Pending Orders To Complete",Toast.LENGTH_SHORT).show();
                }
                else {

                    setAdapterforOngoing(orderlist);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                 Log.w("CANCELLED", "loadPost:onCancelled", databaseError.toException());

            }
        };
        morder.addListenerForSingleValueEvent(orderListener);
        morderListener = orderListener;

    }

    //Sets the Ongoing Adapter for Recycler View
    public void  setAdapterforOngoing(ArrayList<OrderItems> orderlist)
    {
        adap=new Ongoing_Adapter(orderlist,this);
        View_Recycler.setAdapter(adap);
    }


    //Method to get the name of current user that is logged in.
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

                // Getting Post failed, log a message
                Log.w("CANCELLED", "loadPost:onCancelled", databaseError.toException());

            }

        };
        mdb.addValueEventListener(user);

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
                textname.setText(ud.getName());
                textmail.setText(ud.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("CANCELLED", "loadPost:onCancelled", databaseError.toException());

            }

        };
        mdb.addValueEventListener(userlistener);


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
        if (id == R.id.action_logout) {
            mAuth.signOut();
            Intent i=new Intent(History.this,LoginActivity.class);
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
        else if (id == R.id.nav_monthlyBills) {
            Intent i = new Intent(this, MonthlyBills.class);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


