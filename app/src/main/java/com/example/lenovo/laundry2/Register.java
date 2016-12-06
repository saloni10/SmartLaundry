package com.example.lenovo.laundry2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Register class
public class Register extends AppCompatActivity {

    EditText uname,room,pass,phone,mail;
    RadioGroup radioHostel;
    RadioButton radiobtn;
    private Firebase mref;
    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Checks connectivity to the network
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isConnected()) {
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_LONG).show();

        }
        mAuth=FirebaseAuth.getInstance();
        mdb= FirebaseDatabase.getInstance().getReference().child("Users");
        mref=new Firebase("https://smartlaundry-da99b.firebaseio.com/").child("Users");
        AuthData curr_user=mref.getAuth();



    }

   public void registerfunc(View view)
    {
        uname =(EditText) findViewById(R.id.name_text_view);
        room =(EditText) findViewById(R.id.room_text_view);
        pass =(EditText) findViewById(R.id.password_text_view);
        phone =(EditText) findViewById(R.id.phone_text_view);
        mail =(EditText) findViewById(R.id.email_text_view);
        radioHostel = (RadioGroup) findViewById(R.id.radioHostel);
        int selectedId = radioHostel.getCheckedRadioButtonId();
        radiobtn = (RadioButton) findViewById(selectedId);
        final String uname1=uname.getText().toString();
        final String room1=room.getText().toString();
        final String pass1=pass.getText().toString();
        final String phone1=phone.getText().toString();
        final String mail1=mail.getText().toString();
       // final String radioval = radiobtn.getText().toString();

        if (isEmailValid(mail1)) {
            if (!TextUtils.isEmpty(uname1) && !TextUtils.isEmpty(room1) && !TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(mail1) && !TextUtils.isEmpty(phone1)) {
                final String radioval = radiobtn.getText().toString();
                mAuth.createUserWithEmailAndPassword(mail1, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String userid = mAuth.getCurrentUser().getUid();
                            Toast.makeText(Register.this, "You have been registered", Toast.LENGTH_SHORT).show();
                            DatabaseReference currentUser = mdb.child(userid);
                            currentUser.child("Name").setValue(uname1);
                            currentUser.child("Room").setValue(room1);
                            currentUser.child("Hostel").setValue(radioval);
                            currentUser.child("Password").setValue(pass1);
                            currentUser.child("Phone").setValue(phone1);
                            currentUser.child("Email").setValue(mail1);
                            Toast.makeText(Register.this, "You have been registered", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Register.this, LoginActivity.class);
                            startActivity(i);

                        }
                    }


                });
            } else {
                Toast.makeText(Register.this, "No field should be left blank", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(Register.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
        }

    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


}


