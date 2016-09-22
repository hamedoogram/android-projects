package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import java.util.ArrayList;

/**
 * Created by hmoussa on 25/05/2015.
 */
public class ExpandableListMainActivity extends Activity {
    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private ExpandableListView expandableListView;
    private int lastExpandedPosition = -1;
    String htmlcode = "<!DOCTYPE html> <html> <head> <title>Page Title</title> </head> <body> <h1>My First Heading</h1> <p>My first paragraph.</p> </body> </html>";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.expendable_list_view);

        // Create Expandable List and set it's properties
        final ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.expendable_list_v);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        // Set the Items of Parent
        setGroupParents();
        // Set The Child Data
        setChildData();

        // Create the Adapter
        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);
        //expandableList.setOnChildClickListener((ExpandableListView.OnChildClickListener) this);
    }

    // method to add parent Items
    public void setGroupParents()
    {
        parentItems.add("Fruits");
        parentItems.add("Flowers");
        parentItems.add("Animals");
        parentItems.add("Birds");
    }

    // method to set child data of each parent
    public void setChildData()
    {

        // Add Child Items for Fruits
        ArrayList<String> child = new ArrayList<String>();
        child.add("Apple");
        child.add("Mango");
        child.add("Banana");
        child.add(htmlcode);

        childItems.add(child);

        // Add Child Items for Flowers
        child = new ArrayList<String>();
        child.add("Rose");
        child.add("Lotus");
        child.add("Jasmine");
        child.add(htmlcode);

        childItems.add(child);

        // Add Child Items for Animals
        child = new ArrayList<String>();
        child.add("Lion");
        child.add("Tiger");
        child.add("Horse");
        child.add(htmlcode);

        childItems.add(child);

        // Add Child Items for Birds
        child = new ArrayList<String>();
        child.add("Parrot");
        child.add("Sparrow");
        child.add("Peacock");
        child.add(htmlcode);

        childItems.add(child);
    }

    public ExpandableListView getExpandableListView() {
        expandableListView = new ExpandableListView(this.getApplicationContext());
        return expandableListView;
    }
}
