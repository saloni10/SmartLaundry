package com.example.lenovo.laundry2;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;



//Basic class that represents Users.
//This class represents the features related to the User.
public class userDetails {

    private String Name;
    private String Room;
    private String Hostel;
    private String Phone;
    private String Email;
    private String Password;


    userDetails()
    {

    }

    /*........Getters & Setters.............*/
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getHostel() {
        return Hostel;
    }

    public void setHostel(String hostel) {
        Hostel = hostel;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    //Mapping of the features to the fields in the firebase storage.
    @Exclude
    public Map<String,Object> toMap()
    {
        HashMap<String,Object> result=new HashMap<>();
        result.put("Name",getName());
        result.put("Email",getEmail());
        result.put("Password",getPassword());
        result.put("Room",getRoom());
        result.put("Hostel",getHostel());
        result.put("Phone",getPhone());

        return result;
    }
}
