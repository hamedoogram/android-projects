package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by hmoussa on 14/05/2015.
 */
public class QueryList extends Activity implements AdapterView.OnItemClickListener {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querylist);

        ListView listView = (ListView) findViewById(R.id.listView_querylist);
        listView.setAdapter(new UserItemAdapter_query(this, R.layout.querylistitem, Global_Values.QueryResultlist));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public class UserItemAdapter_query extends ArrayAdapter<query_item> {
        private ArrayList<query_item> tasks;
        public UserItemAdapter_query(Context context, int textViewResourceId, ArrayList<query_item> tasks) {
            super(context, textViewResourceId, tasks);
            this.tasks = tasks;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.querylistitem, null);
            }

            query_item task = tasks.get(position);
            if (task != null) {
                TextView title = (TextView) v.findViewById(R.id.querytitle);
                TextView assignto = (TextView) v.findViewById(R.id.queryassign);
                TextView state = (TextView) v.findViewById(R.id.querystate);
                TextView description = (TextView) v.findViewById(R.id.querydescription);
                if (title != null) {
                    title.setText(task.Title);
                }

                if(description != null) {
                    description.setText("Description: " + Html.fromHtml(task.Description));
                }

                if (assignto != null) {
                    assignto.setText("Assign to: " + task.Assignto);
                }

                if(state != null) {
                    state.setText("State: " + task.State );
                }
            }
            return v;
        }
    }
}
