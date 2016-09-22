package com.ksu.team.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmoussa on 22/02/2015.
 * Refrance for SQL Helper http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CallManager";

    // Contacts table name
    private static final String TABLE_CALLS = "calls";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PhoneNumber = "PhoneNumber";
    private static final String KEY_Path = "Path";
    private static final String KEY_Duration = "Duration";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Calls_TABLE = "CREATE TABLE " + TABLE_CALLS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PhoneNumber + " TEXT,"
                + KEY_Path + " TEXT" + KEY_Duration + " TEXT" + ")";
        db.execSQL(CREATE_Calls_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Adding new call
    public void addCall(Call call)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PhoneNumber, call.getPhoneNumber()); // Contact Phone Number
        values.put(KEY_Path, call.getPath()); // Contact Path
        //values.put(KEY_Duration, call.getDuration()); // Contact Duration

        // Inserting Row
        db.insert(TABLE_CALLS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single call
    //public Call getCall(int id) {}

    // Getting All Calls
    public List<Call> getAllCalls()
    {
        List<Call> callList = new ArrayList<Call>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CALLS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Call obj_call = new Call();
                obj_call.setID(Integer.parseInt(cursor.getString(0)));
                obj_call.setPhoneNumber(cursor.getString(1));
                obj_call.setPath(cursor.getString(2));
                //obj_call.setDuration(cursor.getString(3));
                // Adding contact to list
                callList.add(obj_call);
            } while (cursor.moveToNext());
        }

        // return contact list
        return callList;
    }

    /**
     * // Getting Calls Count
     public int getCallsCount() {}
     // Updating single contact
     public int updateCall(Call call) {}

     // Deleting single contact
     public void deleteCall(Call call) {}
     * @return
     */

}
