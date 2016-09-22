package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hmoussa on 25/05/2015.
 */
public class MyExpandableAdapter extends BaseExpandableListAdapter
{

    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;

    // constructor
    public MyExpandableAdapter(ArrayList<String> parents, ArrayList<Object> childern)
    {
        this.parentItems = parents;
        this.childtems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity)
    {
        this.inflater = inflater;
        this.activity = activity;
    }

    // method getChildView is called automatically for each child view.
    //  Implement this method as per your requirement
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if(!isLastChild){

            child = (ArrayList<String>) childtems.get(groupPosition);

            TextView textView = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_view, null);
            }

            // get the textView reference and set the value
            textView = (TextView) convertView.findViewById(R.id.textViewChild);
            String str = String.valueOf(childPosition);
            Log.println( 0,"","child position " + str);
            textView.setText(child.get(childPosition));

            // set the ClickListener to handle the click event on child item
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, child.get(childPosition),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            child = (ArrayList<String>) childtems.get(groupPosition);

            TextView textView = null;
            WebView webv = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_view_description, null);
            }

            // get the textView reference and set the value
            webv = (WebView) convertView.findViewById(R.id.webViewChild);
            //textView.setText(child.get(childPosition));
            webv.getSettings().setJavaScriptEnabled(true);
            webv.loadDataWithBaseURL("", child.get(childPosition), "text/html", "UTF-8", "");

            // set the ClickListener to handle the click event on child item
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, child.get(childPosition),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }

    // method getGroupView is called automatically for each parent item
    // Implement this method as per your requirement
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parent_view, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    @Override
    public int getGroupCount()
    {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition)
    {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition)
    {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}