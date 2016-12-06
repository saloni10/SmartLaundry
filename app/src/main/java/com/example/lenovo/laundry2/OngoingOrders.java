package com.example.lenovo.laundry2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

//This activity dislays the ongoing orders in the recycler view.
//First it gets the data from the firebase storage and stores it in arraylist.
//This arraylist is passed to the adapter as MODEL which it then binds to the view holder.
public class OngoingOrders extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ValueEventListener morderListener;
    private DatabaseReference morder;
    private RecyclerView View_Recycler;
    public  Ongoing_Adapter adap;
    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    NavigationView navigationView;

    TextView textName;
    TextView text;
    String userid;
    String n;
    ArrayList<OrderItems> orderlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.email_nav_ongoi_order);
        textName = (TextView) header.findViewById(R.id.name_nav_ongoi_order);

        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent i = new Intent(OngoingOrders.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        };

        //Checks the network Connectivity
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

        View_Recycler=(RecyclerView)findViewById(R.id.rec_list_ongoing);
        View_Recycler.setLayoutManager(new LinearLayoutManager(this));
        Name();
        morder = FirebaseDatabase.getInstance().getReference().child("Orders");


        ValueEventListener orderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // User user = dataSnapshot.getValue(User.class);
                for(DataSnapshot dsp: dataSnapshot.getChildren())
                {

                    OrderItems order= dsp.getValue(OrderItems.class);
                    userid=mAuth.getCurrentUser().getUid();
                    if(!order.getWhenConfirmed().equals("#1") && order.getWhenCompleted().equals("#2") && order.getUsername().equals(n))
                    {
                        orderlist.add(order);
                    }

                }
                if(orderlist.size()==0)
                {
                    Toast.makeText(getApplicationContext(), "No Pending Orders To Complete",Toast.LENGTH_SHORT).show();
                }
                else {

                    setAdapter(orderlist);

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


    public void setAdapter(ArrayList<OrderItems> orderlist)
    {
        adap=new Ongoing_Adapter(orderlist,this);
        View_Recycler.setAdapter(adap);
    }

    //Retrieve the names of all
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
        mAuth.addAuthStateListener(mAuthListener);

        ValueEventListener userlistener= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                text.setText(ud.getEmail());
                textName.setText(ud.getName());

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
        getMenuInflater().inflate(R.menu.ongoing_orders, menu);
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
            Intent i=new Intent(OngoingOrders.this,LoginActivity.class);
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
                Intent i = new Intent(this, RateList.class);
                startActivity(i);
            } else if (id == R.id.nav_ongoingOrders) {
                Intent i = new Intent(this, OngoingOrders.class);
                startActivity(i);
            } else if (id == R.id.nav_history) {
                Intent i = new Intent(this, History.class);
                startActivity(i);
            } else if (id == R.id.nav_account) {
                Intent i = new Intent(this, Account.class);
                startActivity(i);
            }  else if (id == R.id.nav_info) {
                Intent i = new Intent(this, Information.class);
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