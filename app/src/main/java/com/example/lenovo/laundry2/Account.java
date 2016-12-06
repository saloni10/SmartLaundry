package com.example.lenovo.laundry2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    EditText name;
    EditText room;
    RadioGroup hostel;
    RadioButton girls;
    RadioButton boys;
    EditText mail;
    EditText phone;
    TextView textName;
    TextView text;

    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String userid;
    NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        //Resolving the text views
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.email_nav_acc);
        textName = (TextView) header.findViewById(R.id.name_nav_acc);
        phone=(EditText) findViewById(R.id.mobileEdit);
        name = (EditText)findViewById(R.id.nameEdit);
        room = (EditText)findViewById(R.id.roomEdit);
        mail = (EditText)findViewById(R.id.emailEdit);
        phone = (EditText)findViewById(R.id.mobileEdit);
        hostel=(RadioGroup)findViewById(R.id.radioGroup);
        girls = (RadioButton) findViewById(R.id.radioButton);
        boys = (RadioButton) findViewById(R.id.radioButton2);



    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //ValueEventListener to get the user details.
        ValueEventListener userlistener= new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                userDetails ud=dataSnapshot.getValue(userDetails.class);
                text.setText(ud.getEmail());
                textName.setText(ud.getName());
                name.setText(ud.getName());
                room.setText(ud.getRoom());
                mail.setText(ud.getEmail());
                phone.setText(ud.getPhone());
                if(ud.getHostel().equals("Girls")) {
                    girls.setChecked(true);
                    boys.setChecked(false);
                }
                else {
                    boys.setChecked(true);
                    girls.setChecked(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("CANCELLED", "loadPost:onCancelled", databaseError.toException());

            }

        };
        mdb.addValueEventListener(userlistener);

    }


    //Method to change Password
    //Opens up a new dialog to get the new password & updates it.
    public void changePassword(View v)
    {

        final EditText newPassword = new EditText(this);
        newPassword.setHint("Enter New Password");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Change Password");
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(newPassword);
        dialog.setView(layout);
        dialog.setPositiveButton("Change", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pass=newPassword.getText().toString();

                if(!TextUtils.isEmpty(pass)) {

                    DatabaseReference currentUser = mdb;
                    currentUser.child("Password").setValue(pass);


                    Toast.makeText(Account.this, "Password Changed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Account.this, "Cannot leave the field empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.create();
        dialog.show();
    }

    //Passes the intent to Edit() Activity-that makes the text views editable
    public void edit(View v)
    {
        Intent i=new Intent(this,Account_Edit.class);
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
        getMenuInflater().inflate(R.menu.account, menu);
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
            Intent i=new Intent(Account.this,LoginActivity.class);
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
        }
        /*else if (id == R.id.nav_monthlyBills) {
            Intent i=new Intent(this,MonthlyBills.class);
            startActivity(i);*/
         else if (id == R.id.nav_history) {
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
        else if (id == R.id.notifications) {
            Intent i=new Intent(this,OrderNotifications.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
