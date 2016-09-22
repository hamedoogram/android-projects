package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by hmoussa on 25/03/2015.
 */
public class Global_Values {
    public static  String userName = "";
    public static  String password = "";
    public static  String collection = "";
    public static  String teamProject = "";
    public static  String domain = "";
    public static  String URL = "http://10.102.3.161/";
    public static ArrayList<ArrayList<String>> collectionlist;
    public static ArrayList<ArrayList<String>> USerslist;
    public static ArrayList<query_item> QueryResultlist;
    public static ArrayList<ArrayList<String>> collectionlist_aftercounting;
    public static ArrayList<ArrayList<String>> collectionlist_afterFiltering;
    public static Activity activity;
    public static ArrayList<ArrayList<String>> Department_collectionlist;
    public static ArrayList<ArrayList<String>> Status_collectionlist;
    public static ArrayList<ArrayList<String>> Selected_Status_collectionlist;
    public static ArrayList<ArrayList<String>> collectionlist_filter;
    public static ArrayList<String> teamvalue;
    public static ArrayList<String> teamname;
    public static ArrayList<String> statusname;
    public static ArrayList<String> statusvalue;
    public static ArrayList<Item> users;
    public static ArrayList<Item> users_Statis;
    public static ArrayList<task_workitem> task_workitems;
    public static ArrayList<bug_workitem> bug_workitems;
    public static  int stateORdepartement = 0;
    public static String bug_state ="";
    public static String query_assignto = "";
    public static String query_type = "";
    public static String query_state = "";
    public static QueryActivity QueryActiv;
    public static ArrayList<bug_workitem> GroupList_Bug;

}
