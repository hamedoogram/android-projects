package com.yalantis.guillotine.sample.data;

import android.content.Context;
import android.util.Log;

import com.survey.library.appprocess.process.Calculations;
import com.survey.library.appprocess.process.Survey;
import com.survey.library.appprocess.sqlite.DatabaseHandler;

import java.util.List;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class DBConnector {

    // Method Name: addNewSurvey
    // Inputs: Context context, String name, int age, int gender, int mig, int drugs
    // Output: Add New Survey to SQlite DB
    // Function: 1- Create a new survey object  2- pass survey object to Sqlite handler to add it.
    public static void addNewSurvey(Context context, String name, int age, int gender, int mig, int drugs){
        try{
            Survey survey = new Survey(name,age,gender,mig,drugs,0);
            DatabaseHandler db = new DatabaseHandler(context);
            db.addSurvey(survey);
        }catch(Exception ex){
            Log.e("Error in DB Connector: ", ex.getMessage());
        }
    }


    // Method Name: searchSurveys
    // Inputs: Context context, String name
    // Output: Get all surveys with the passed patient name from stored surveys in SQlite DB
    // Function: 1- Create a new dbhandler object  2- pass survey patient name object to query it in Sqlite db. 3- Retrieve list of found surveys or null.
    public static List<Survey> searchSurveys(Context context, String name){
        List<Survey> listSurveys;
        try{
            DatabaseHandler db = new DatabaseHandler(context);
            listSurveys = db.getAllSurveys(name);
            return listSurveys;
        }catch(Exception ex){
            Log.e("Error in DB Connector: ", ex.getMessage());
        }
        return null;
    }

    // Method Name: getSurveyResults
    // Inputs: int age, int gender, int mig, int drugs
    // Output: return survey results
    // Function: 1- send needed params to calculate method
    public static int getSurveyResults(int age, int gender, int mig, int drugs){
        return Calculations.calculateResults(age,gender,mig,drugs);
    }

}