package com.example.lenovo.laundry2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText uname,room,pass,phone,mail;
    RadioGroup radioHostel;
    RadioButton radiobtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        // find the radiobutton by returned id
        radiobtn = (RadioButton) findViewById(selectedId);
        DBHandler myDb=new DBHandler(this,null,null,1);
        myDb.Register(uname.getText().toString(),room.getText().toString(),radiobtn.getText().toString(),phone.getText().toString(),mail.getText().toString(),pass.getText().toString());
        Toast.makeText(this,"You have been registered",Toast.LENGTH_SHORT).show();
        uname.setText("");
        room.setText("");
        pass.setText("");
        phone.setText("");
        mail.setText("");
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);



    }
}
