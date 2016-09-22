package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hmoussa on 13/05/2015.
 */
public class WorkItemBugList extends Activity implements AdapterView.OnItemClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfsbuglist);

        ArrayList<bug_workitem> bugs = new ArrayList<bug_workitem>();
        bug_workitem wi1 = new bug_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        bug_workitem wi2 = new bug_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        bug_workitem wi3 = new bug_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        bug_workitem wi4 = new bug_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        bugs.add(wi1); bugs.add(wi4); bugs.add(wi3); bugs.add(wi2);
        //Global_Values.bug_workitems = bugs;

        Global_Values.GroupList_Bug = Global_Values.bug_workitems;

        ListView listView = (ListView) findViewById(R.id.listView_tfsbugs);
        listView.setAdapter(new UserItemAdapter3(this, R.layout.buglistitem, Global_Values.bug_workitems));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public class UserItemAdapter3 extends ArrayAdapter<bug_workitem> {
        private ArrayList<bug_workitem> bugs;
        public UserItemAdapter3(Context context, int textViewResourceId, ArrayList<bug_workitem> bugs) {
            super(context, textViewResourceId, bugs);
            this.bugs = bugs;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.buglistitem, null);
            }

            bug_workitem bug = bugs.get(position);
            if (bug != null) {
                TextView title = (TextView) v.findViewById(R.id.wi_bug_title);
                TextView assignto = (TextView) v.findViewById(R.id.wi_bug_assign);
                TextView date = (TextView) v.findViewById(R.id.wi_bug_createddate);
                TextView description = (TextView) v.findViewById(R.id.wi_bug_description);
                if (title != null) {
                    title.setText(bug.Title);
                }

                if(description != null) {
                    description.setText("Description: " + Html.fromHtml(bug.Description));
                }

                if (assignto != null) {
                    assignto.setText("Assign to: " + bug.Assignto);
                }

                if(date != null) {
                    date.setText("Created Date: " + bug.CreatedDate );
                }
            }
            return v;
        }
    }
}
