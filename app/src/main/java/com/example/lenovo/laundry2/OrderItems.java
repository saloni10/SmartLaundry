package com.example.lenovo.laundry2;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


//Basic class that represents OrderItems.
//This class represents the features related to the a Order placed by the user.
public class OrderItems {


    OrderItems()
    {

    }

    private int Bedsheets;
    private int Shirts;
    private int Lowers;
    private int Others;
    private int TotalQTY;
    private String UserId;
    private String TypeOfOrders;
    private String PickupTime;
    private int TotalPrice;
    private String PickupDate;
    private String Hostel;
    private String WhenPlaced;
    private String WhenConfirmed;
    private String WhenCompleted;
    private String Room;
    private String Username;
    private int Month;

    /*........Getters & Setters.............*/

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    private String OrderID;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getHostel() {
        return Hostel;
    }

    public void setHostel(String hostel) {
        Hostel = hostel;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getWhenPlaced() {
        return WhenPlaced;
    }

    public void setWhenPlaced(String whenPlaced) {
        WhenPlaced = whenPlaced;
    }

    public String getWhenConfirmed() {
        return WhenConfirmed;
    }

    public void setWhenConfirmed(String whenConfirmed) {
        WhenConfirmed = whenConfirmed;
    }

    public String getWhenCompleted() {
        return WhenCompleted;
    }

    public void setWhenCompleted(String whenCompleted) {
        WhenCompleted = whenCompleted;
    }

    public String getPickupDate() {
        return PickupDate;
    }

    public void setPickupDate(String pickupDate) {
        PickupDate = pickupDate;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getTypeOfOrders() {
        return TypeOfOrders;
    }

    public void setTypeOfOrders(String typeOfOrders) {
        TypeOfOrders = typeOfOrders;
    }

    public int getTotalQTY() {
        return TotalQTY;
    }

    public void setTotalQTY(int totalQTY) {
        TotalQTY = totalQTY;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getLowers() {
        return Lowers;
    }

    public void setLowers(int lowers) {
        Lowers = lowers;
    }

    public int getOthers() {
        return Others;
    }

    public void setOthers(int others) {
        Others = others;
    }

    public int getShirts() {
        return Shirts;
    }

    public void setShirts(int shirts) {
        Shirts = shirts;
    }

    public int getBedsheets() {
        return Bedsheets;
    }

    public void setBedsheets(int bedsheets) {
        Bedsheets = bedsheets;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }


    //Mapping of the features to the fields in the firebase storage.
    @Exclude
    public Map<String,Object> toMap()
    {
        HashMap<String,Object> result=new HashMap<>();
        result.put("UserId",getUserId());
        result.put("Bedsheets",getBedsheets());
        result.put("Shirts",getShirts());
        result.put("Lowers",getLowers());
        result.put("Others",getOthers());
        result.put("TotalPrice",getTotalPrice());
        result.put("TotalQty",getTotalQTY());
        result.put("WhenPlaced",getWhenPlaced());
        result.put("WhenConfirmed",getWhenConfirmed());
        result.put("WhenCompleted",getWhenCompleted());
        result.put("TypeOfOrders",getTypeOfOrders());
        result.put("PickupDate",getPickupDate());
        result.put("PickupTime",getPickupTime());
        result.put("Hostel",getHostel());
        result.put("Room",getRoom());
        result.put("Username",getUsername());
        result.put("OrderID",getOrderID());
        result.put("Month",getMonth());

        return result;
    }

}
