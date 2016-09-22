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
public class WorkItemTaskList extends Activity implements AdapterView.OnItemClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfstasklist);

        ArrayList<task_workitem> tasks = new ArrayList<task_workitem>();
        task_workitem wi1 = new task_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        task_workitem wi2 = new task_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        task_workitem wi3 = new task_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        task_workitem wi4 = new task_workitem("Title1", "hamed Moussa","29/11/1986","Description 1");
        tasks.add(wi1); tasks.add(wi4); tasks.add(wi3); tasks.add(wi2);
        //Global_Values.task_workitems = tasks;

        ListView listView = (ListView) findViewById(R.id.listView_tfstasks);
        listView.setAdapter(new UserItemAdapter2(this, R.layout.tasklistitem, Global_Values.task_workitems));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public class UserItemAdapter2 extends ArrayAdapter<task_workitem> {
        private ArrayList<task_workitem> tasks;
        public UserItemAdapter2(Context context, int textViewResourceId, ArrayList<task_workitem> tasks) {
            super(context, textViewResourceId, tasks);
            this.tasks = tasks;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.tasklistitem, null);
            }

            task_workitem task = tasks.get(position);
            if (task != null) {
                TextView title = (TextView) v.findViewById(R.id.wititle);
                TextView assignto = (TextView) v.findViewById(R.id.wiassign);
                TextView date = (TextView) v.findViewById(R.id.wicreateddate);
                TextView description = (TextView) v.findViewById(R.id.widescription);
                if (title != null) {
                    title.setText(task.Title);
                }

                if(description != null) {
                    description.setText("Description: " + Html.fromHtml(task.Description));
                }

                if (assignto != null) {
                    assignto.setText("Assign to: " + task.Assignto);
                }

                if(date != null) {
                    date.setText("Created Date: " + task.CreatedDate );
                }
            }
            return v;
        }
    }
}
