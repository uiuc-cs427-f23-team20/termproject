package edu.uiuc.cs427app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper extends SQLiteOpenHelper {
    // public static final String databaseName = "login.db";
    public DataHelper(Context context) {
        super(context, "cityApp.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB){
        // create users, cities, and users_cities table, adds cities records
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
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(4, 'San Francisco');");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(5, 'Seattle');");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(6, 'New York City');");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name) VALUES(7, 'Miami');");

        myDB.execSQL(createUsersTable);
        myDB.execSQL(createCitiesTable);
        myDB.execSQL(createUsersCitiesTable);
        for (String baseCity : insertBaseCities) {
            myDB.execSQL(baseCity);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        // drops users table
        myDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password, int uiconfig){
        // returns True if new user was successfully created
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("netid", username);
        contentValues.put("password", password);
        contentValues.put("uiconfig", uiconfig);

        long result = myDB.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkUsername(String username){
        // returns True if netid already exists in database
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkusernamePassword(String username, String password){
        // returns True if netid and password are validated
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkUIConfig(String username){
        // returns True if dark mode enabled for user
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ?", new String[]{username});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            @SuppressLint("Range") int uiconfig = cursor.getInt(cursor.getColumnIndex("uiconfig"));
            return uiconfig == 1 ? true : false;
        }
        return false;
    }

    public Map<String, String> getAllCities() {
        Map<String, String> citiesIdToName = new HashMap<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from cities", null);
        while (cursor.moveToNext()) {
            String cityId = cursor.getString(0);
            String cityName = cursor.getString(1);
            citiesIdToName.put(cityId, cityName);
        }
        return citiesIdToName;
    }

    public Boolean insertUsersCities(String userId, String cityId) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        List<String> userCitiesId = getUserCitiesId(userId);
        if (userCitiesId.contains(cityId)) {
            return true;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("citi_id", cityId);

        long result = myDB.insert("user_cities", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteUserCity(String userId, String citiId) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        String tempCitiId = "2";

        // Delete the record from the user_cities table
        int deletedRows = myDB.delete("user_cities", "user_id = ? and citi_id = ?",
                new String[] {userId, citiId});

        // Check if any record were deleted
        return deletedRows>0;
    }

    public Map<String, String> getUserCitiesIdToCityName(String userId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from user_cities uc join cities c " +
                "on uc.citi_id = c.citi_id where user_id = ?", new String[]{userId});

        Map<String, String> cityIdToCityMap = new HashMap<>();
        int cityIdColIdx = cursor.getColumnIndex("citi_id");
        int cityNameColIdx = cursor.getColumnIndex("citi_name");
        while (cursor.moveToNext()) {
            String cityId = cursor.getString(cityIdColIdx);
            String cityName = cursor.getString(cityNameColIdx);
            cityIdToCityMap.put(cityId, cityName);
        }
        return cityIdToCityMap;
    }

    public List<String> getUserCitiesId(String userId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from user_cities where user_id = ?", new String[]{userId});

        List<String> userCities = new ArrayList<>();
        while (cursor.moveToNext()) {
            String citiesId = cursor.getString(1);
            userCities.add(citiesId);
        }
        return userCities;
    }
}
