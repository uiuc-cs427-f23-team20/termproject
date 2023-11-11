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

/**
 * DataHelper class manages the SQLite database operations for user and city data.
 */
public class DataHelper extends SQLiteOpenHelper {
    // public static final String databaseName = "login.db";
    public DataHelper(Context context) {
        super(context, "cityApp.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB){
        // Called when the database is created for the first time.
        // create users, cities, and users_cities table
        String createUsersTable = "create table users (netid varchar(20) not null, " +
                "password varchar(20) not null, uiconfig int not null default 0, " +
                "email varchar(50), name varchar(50), primary key(netid));";

        String createCitiesTable = "create table cities (citi_id varchar(200) not null,  " +
                "citi_name varchar(50) not null,  state varchar(50),  " +
                "country varchar(50), latitude decimal(8,6), " +
                "longitude decimal(9,6),  map_api_id int, " +
                "weather_api_id int, popular int default 0, " +
                "primary key(citi_id)) ;";

        String createUsersCitiesTable = "create table user_cities(user_id varchar(20) not null, " +
                "citi_id varchar(200) not null, CONSTRAINT FK_USER FOREIGN KEY(user_id) REFERENCES users(netid), " +
                "CONSTRAINT FK_CITY FOREIGN KEY(citi_id) REFERENCES cities(citi_id), " +
                "primary key(user_id,citi_id) ) ;";

        // adds cities records - subject to change in milestone 4
        List<String> insertBaseCities = new ArrayList<>();
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(1, 'Champaign', 40.116329, -88.243523);");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(2, 'Chicago', 41.878113, -87.629799);");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(3, 'Los Angeles', 34.052235, -118.243683);");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(4, 'San Francisco', 37.774929, -122.419418);");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(5, 'Seattle', 47.606209, -122.332069);");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(6, 'New York City', 40.730610, -73.935242);");
        insertBaseCities.add("INSERT INTO cities (citi_id, citi_name, latitude, longitude) VALUES(7, 'Miami', 25.761681, -80.191788);");

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

    public Boolean insertUserData(String username, String password, int uiConfig){
        // this method insert new user's username/password/UI Customization data into user table
        // returns True if new user was successfully created
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("netid", username);
        contentValues.put("password", password);
        contentValues.put("uiconfig", uiConfig);

        long result = myDB.insert("users", null, contentValues);
        return result != -1;
    }
    public Boolean checkUsername(String username){
        // returns True if username(netid) already exists in database
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ?", new String[]{username});
        Boolean isValidated = cursor.getCount() > 0;
        cursor.close();
        return isValidated;
    }
    public Boolean checkUsernamePassword(String username, String password){
        // returns True if username(netid) and password are validated
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ? and password = ?", new String[]{username, password});
        Boolean isValidated = cursor.getCount() > 0;
        cursor.close();
        return isValidated;
    }

    public Boolean checkUIConfig(String username){
        // returns True if dark mode enabled for user
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where netid = ?", new String[]{username});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            @SuppressLint("Range") int uiconfig = cursor.getInt(cursor.getColumnIndex("uiconfig"));
            cursor.close();
            return uiconfig == 1;
        }
        return false;
    }

    public Map<String, String> getAllCities() {
        // returns a hashmap of all cityIds to cityNames in the cities table
        Map<String, String> citiesIdToName = new HashMap<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from cities", null);
        while (cursor.moveToNext()) {
            String cityId = cursor.getString(0);
            String cityName = cursor.getString(1);
            citiesIdToName.put(cityId, cityName);
        }
        cursor.close();
        return citiesIdToName;
    }

    public boolean insertUsersCities(String userId, String cityId) {
        // this method insert userId and cityId into user_cities table
        // returns true if the insertion is successful
        SQLiteDatabase myDB = this.getWritableDatabase();
        List<String> userCitiesId = getUserCitiesId(userId);
        if (userCitiesId.contains(cityId)) {
            return true;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("citi_id", cityId);

        long result = myDB.insert("user_cities", null, contentValues);
        return result != -1;
    }

    public boolean deleteUserCity(String userId, String citiId) {
        // Delete the record from the user_cities table
        // returns true if the insertion is successful
        SQLiteDatabase myDB = this.getWritableDatabase();
        int deletedRows = myDB.delete("user_cities", "user_id = ? and citi_id = ?",
                new String[] {userId, citiId});
        return deletedRows>0;
    }

    public Map<String, String> getUserCitiesIdToCityName(String userId) {
        // Retrieves a map of city IDs to city names for cities associated with the given user ID
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
        cursor.close();
        return cityIdToCityMap;
    }

    public List<String> getUserCitiesId(String userId) {
        // Retrieves a list of cityIDs associated with the given username
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from user_cities where user_id = ?", new String[]{userId});

        List<String> userCities = new ArrayList<>();
        while (cursor.moveToNext()) {
            String citiesId = cursor.getString(1);
            userCities.add(citiesId);
        }
        cursor.close();
        return userCities;
    }

    public List<Double> getCoords(String cityId) {
        // Retrieves latitude, longitude of city
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from cities where citi_id = ?", new String[]{cityId});
        cursor.moveToFirst();

        List<Double> coordinates = new ArrayList<>();
        int latIndex = cursor.getColumnIndex("latitude");
        int longIndex = cursor.getColumnIndex("longitude");
        Double latitude = cursor.getDouble(latIndex);
        Double longitude = cursor.getDouble(longIndex);
        coordinates.add(latitude);
        coordinates.add(longitude);
        cursor.close();
        return coordinates;
    }
    
    public CityTable getCitiesByCityId(String cityId) {
        // Retrieves a map of city IDs to city names for cities associated with the given user ID
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from cities c  " +
                " where citi_id = ?", new String[]{cityId});

        CityTable cityTable = new CityTable();

        int cityNameColIdx = cursor.getColumnIndex("citi_name");
        int stateColIdx = cursor.getColumnIndex("state");
        int countryColIdx = cursor.getColumnIndex("country");
        int latitudeColIdx = cursor.getColumnIndex("latitude");
        int longitudeColIdx = cursor.getColumnIndex("longitude");
        while (cursor.moveToNext()) {
            cityTable.setCitiId(cityId);
            cityTable.setCitiName(cursor.getString(cityNameColIdx));
            cityTable.setState(cursor.getString(stateColIdx));
            cityTable.setCountry(cursor.getString(countryColIdx));
            cityTable.setLatitude(cursor.getDouble(latitudeColIdx));
            cityTable.setLongitude(cursor.getDouble(longitudeColIdx));
        }
        cursor.close();
        return cityTable;
    }
}
