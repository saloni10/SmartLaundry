package com.example.lenovo.laundry2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account_Edit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText name;
    EditText room;
    RadioGroup hostel;
    RadioButton radiobtn;
    RadioButton girls;
    RadioButton boys;
    EditText mail;
    EditText phone ;
    NavigationView navigationView;

    TextView textName;
    TextView text;



    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String userid;
    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.email_nav_accedit);
        textName = (TextView) header.findViewById(R.id.name_nav_accedit);


        name = (EditText)findViewById(R.id.nameEdit);
        room = (EditText)findViewById(R.id.roomEdit);
        mail = (EditText)findViewById(R.id.emailEdit);
        phone = (EditText)findViewById(R.id.mobileEdit);
        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //  mAuth.addAuthStateListener(mAuthListener);

        ValueEventListener userlistener= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                name.setText(ud.getName());
                room.setText(ud.getRoom());
                mail.setText(ud.getEmail());
                phone.setText(ud.getPhone());
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

    public void editApply(View v)
    {
        final String uname1=name.getText().toString();
        final String room1=room.getText().toString();
        final String phone1=phone.getText().toString();
        final String mail1=mail.getText().toString();
        String userid=mAuth.getCurrentUser().getUid();

        DatabaseReference currentUser=mdb;

        if(!TextUtils.isEmpty(uname1)&&!TextUtils.isEmpty(room1)&& !TextUtils.isEmpty(mail1)&& !TextUtils.isEmpty(phone1)) {
            currentUser.child("Name").setValue(uname1);
            currentUser.child("Room").setValue(room1);

            currentUser.child("Phone").setValue(phone1);
            currentUser.child("Email").setValue(mail1);

            Toast.makeText(this, "Changes applied", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Account.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "No field can be left blank", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account__edit, menu);
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
            Intent i=new Intent(Account_Edit.this,LoginActivity.class);
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
        }else if (id == R.id.nav_monthlyBills) {
            Intent i = new Intent(this, MonthlyBills.class);
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
}
