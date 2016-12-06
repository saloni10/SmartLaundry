package com.example.lenovo.laundry2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

//Confirms order summary and then passes control to the main User Page.
public class place_order2 extends AppCompatActivity {

    NavigationView navigationView;
    String userid;
    TextView textname;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order2);

    }
    public void placeOrderSummary(View v)
    {
        Toast.makeText(this,"Order Placed",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,UserPage.class);
        startActivity(i);
    }
}
