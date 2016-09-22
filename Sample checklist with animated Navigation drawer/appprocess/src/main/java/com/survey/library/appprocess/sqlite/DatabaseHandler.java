package com.survey.library.appprocess.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.survey.library.appprocess.process.Calculations;
import com.survey.library.appprocess.process.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "surveyManager";

    // Contacts table name
    private static final String TABLE_Surveys = "surveys";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_Name = "name";
    private static final String KEY_Age = "age";
    private static final String KEY_Gender = "gender";
    private static final String KEY_Migraines = "migraines";
    private static final String KEY_Drugs = "drugs";
    private static final String KEY_Result = "result";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Surveys + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Name + " TEXT," + KEY_Age + " INTEGER," + KEY_Gender + " INTEGER," + KEY_Migraines + " INTEGER,"
                + KEY_Drugs + " TEXT," + KEY_Result + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Surveys);

        // Create tables again
        onCreate(db);
    }

    // Adding new Survey
    public void addSurvey(Survey survey) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Name, survey.getName()); // Name
        values.put(KEY_Age, survey.getAge()); // Age
        values.put(KEY_Gender, survey.getGender()); // Gender
        values.put(KEY_Migraines, survey.getMigraines()); // Migraines
        values.put(KEY_Drugs, survey.getDrugs()); // Drugs
        values.put(KEY_Result, Calculations.calculateResults(survey.getAge(), survey.getGender(), survey.getMigraines(), survey.getDrugs())); // final survey result
        // Inserting Row
        db.insert(TABLE_Surveys, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Survey
    public Survey getSurvey(String Name) {
        return null;
    }

    // Getting All Surveys
    public List<Survey> getAllSurveys(String Name) {
        List<Survey> surveysList = new ArrayList<Survey>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Surveys + " WHERE "+KEY_Name+" LIKE '%" + Name + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Survey survey = new Survey(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
                // Adding survey to list
                surveysList.add(survey);
            } while (cursor.moveToNext());
        }
        // return surveys list
        return surveysList;
    }
}