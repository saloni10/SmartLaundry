package com.example.lenovo.laundry2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//The main launcher activity
//Displays the rate list and enables user to place order.
public class UserPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textname;
    private TextView text;
    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    NavigationView navigationView;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        text = (TextView) header.findViewById(R.id.mail_nav);
        textname = (TextView) header.findViewById(R.id.name_nav);


        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent i = new Intent(UserPage.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        };
        Intent i=getIntent();
        String email=i.getStringExtra("email");
        text.setText(email);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isConnected()) {
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_LONG).show();

        }
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
            Intent i=new Intent(UserPage.this,LoginActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart()
    {
        super.onStart();

        ValueEventListener userlistener= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                textname.setText(ud.getName());
                text.setText(ud.getEmail());

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
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_page, menu);
        return true;
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

    //Passes the control to place order
    public void placeOrder(View v)
    {
        Intent i=new Intent(this,Place_Order.class);
        startActivity(i);

    }
}
