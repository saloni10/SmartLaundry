package com.example.lenovo.laundry2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "laundry.db";
    //private static final String TABLE_LOGIN = "login";
    private static final String TABLE_REGISTER = "register";
    private static final String COLUMN_UMAIL = "umail";
    private static final String COLUMN_PASSWORD = "pass";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ROOM = "room";
    private static final String COLUMN_HOSTEL = "hostel";
    private static final String COLUMN_PHONE = "phone";







    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        SQLiteDatabase obj=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    //    String query1="CREATE TABLE " +TABLE_LOGIN+ "(" +COLUMN_UMAIL+ " TEXT," +COLUMN_PASSWORD+ " TEXT)";
      //  db.execSQL(query1);
        String query2="CREATE TABLE " +TABLE_REGISTER+ "(" +COLUMN_NAME+ " TEXT," +COLUMN_ROOM+ " TEXT," +COLUMN_HOSTEL+ " TEXT," +COLUMN_PHONE+ " TEXT," + COLUMN_UMAIL+ " TEXT," +COLUMN_PASSWORD+ " TEXT)";
        db.execSQL(query2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

       // db.execSQL("DROP TABLE IF EXISTS"+TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_REGISTER);
        onCreate(db);
    }


    public Boolean checkDetails(String email,String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String lquery = "SELECT * FROM " + TABLE_REGISTER;
        Cursor c = db.rawQuery(lquery, null);

        c.moveToFirst();

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("umail")).equals(email) && c.getString(c.getColumnIndex("pass")).equals(password)) {
                return true;
            }

        }
        return false;
    }

    public void Register(String name,String room,String hostel,String phone,String email,String password){

        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_ROOM,room);
        values.put(COLUMN_HOSTEL,hostel);
        values.put(COLUMN_PHONE,phone);
        values.put(COLUMN_UMAIL,email);
        values.put(COLUMN_PASSWORD,password);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_REGISTER,null,values);
       // db.close();

    }

    public void UpdateDetails(String name,String room,String phone,String email){

        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_ROOM,room);
       // values.put(COLUMN_HOSTEL,hostel);
        values.put(COLUMN_PHONE,phone);
        values.put(COLUMN_UMAIL,email);
       // values.put(COLUMN_PASSWORD,password);
        SQLiteDatabase db=getWritableDatabase();
        db.update(TABLE_REGISTER,values,"umail=?",new String[]{email});
        // db.close();

    }


}
