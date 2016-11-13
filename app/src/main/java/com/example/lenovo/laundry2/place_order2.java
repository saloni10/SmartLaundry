package com.example.lenovo.laundry2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class place_order2 extends AppCompatActivity {

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
