package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hmoussa on 25/05/2015.
 */
public class ListActivity_Query extends Activity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expendables_test);

        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                for(int i=0;i<parent.getCount();i++){
                    if(i!=groupPosition){
                        parent.collapseGroup(i);
                    }else{
                        parent.expandGroup(groupPosition);
                    }
                }
                return true;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void createGroupList() {
        groupList = new ArrayList<String>();
        for(int i=0;i<Global_Values.QueryResultlist.size();i++){
            groupList.add(Global_Values.QueryResultlist.get(i).Assignto);
        }
    }
    String htmlcode = "<!DOCTYPE html> <html> <head> <title>Page Title</title> </head> <body> <h1>My First Heading</h1> <p>My first paragraph.</p> </body> </html>";

    private void createCollection() {
        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (int i =0;i<groupList.size();i++) {
            String dsc = "";
            if(Global_Values.QueryResultlist.get(i).Description.toLowerCase().equals("anytype{}")){
                dsc = "No Description Content";
            }else{
                dsc = Global_Values.QueryResultlist.get(i).Description;
            }
            String[] childs = {Global_Values.QueryResultlist.get(i).State,Global_Values.QueryResultlist.get(i).Title,dsc};
            loadChild(childs);
            laptopCollection.put(groupList.get(i), childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


}

