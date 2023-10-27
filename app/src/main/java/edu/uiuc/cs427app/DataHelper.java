package edu.uiuc.cs427app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    // public static final String databaseName = "login.db";
    public DataHelper(Context context) {
        super(context, "cityApp.db", null, 1);
    }


    // create users data table
    @Override
    public void onCreate(SQLiteDatabase myDB){

        String createUsersTable = "create table users (netid varchar(20) not null, " +
                "password varchar(20) not null, uiconfig int not null default 0, " +
                "email varchar(50), name varchar(50), primary key(netid));";

        String createCitiesTable = "create table cities (citi_id varchar(200) not null,  " +
                "citi_name varchar(50) not null,  state varchar(50),  " +
                "country varchar(50), latitude decimal(5,2), " +
                "longitude decimal(5,2),  map_api_id int, " +
                "weather_api_id int, popular int default 0, " +
                "primary key(citi_id)) ;";

        String createUsersCitiesTable = "create table user_cities(user_id varchar(20) not null, " +
                "citi_id varchar(200) not null, CONSTRAINT FK_USER FOREIGN KEY(user_id) REFERENCES users(netid), " +
                "CONSTRAINT FK_CITY FOREIGN KEY(citi_id) REFERENCES cities(citi_id), " +
                "primary key(user_id,citi_id) ) ;";


        List<String> insertBaseCities = new ArrayList<>();
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(1, 'Champaign');");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(2, 'Chicago');");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(3, 'Los Angeles');");

        myDB.execSQL(createUsersTable);
        myDB.execSQL(createCitiesTable);
        myDB.execSQL(createUsersCitiesTable);
        for (String baseCity : insertBaseCities) {
            myDB.execSQL(baseCity);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
    }

    // insert users data
    public Boolean insertData(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("netid", username);
        contentValues.put("password", password);

        long result = myDB.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkusernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
}
