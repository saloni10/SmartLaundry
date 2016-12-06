package com.example.lenovo.laundry2;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Lenovo on 11/16/2016.
 */

//Class to set the context of firebase
public class Laundry2 extends Application {

    @Override
    public  void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);

    }
}
