package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class TFSUsers extends Activity implements AdapterView.OnItemClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfsuserlist);

        ArrayList<Item> users = new ArrayList<Item>();
        Item user1 = new Item("user1", "2 work Items");
        users.add(user1);
        Item user2 = new Item("user2", "2 work Items");
        users.add(user2);
        Item user3 = new Item("user3", "3 work Items");
        users.add(user3);
        ListView listView = (ListView) findViewById(R.id.listView_tfsusers);
        if(Global_Values.stateORdepartement == 1) {
            listView.setAdapter(new UserItemAdapter(this, R.layout.userlistitem, Global_Values.users));
        }else if(Global_Values.stateORdepartement == 2) {
            listView.setAdapter(new UserItemAdapter(this, R.layout.userlistitem, Global_Values.users_Statis));
        }
        listView.setOnItemClickListener(this);
}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Global_Values.stateORdepartement == 1) {
            if(position > -1){

                ArrayList<task_workitem> users = new ArrayList<task_workitem>();
                ArrayList<String> users_check = new ArrayList<String>();
                int wi_count=0;
                for (int i = 0; i < Global_Values.Department_collectionlist.size(); i++) {
                    String name = Global_Values.users.get(position).mTitle;
                    if(name.equals(Global_Values.Department_collectionlist.get(i).get(0).toString())) {
                        wi_count++;
                        task_workitem t_wi = new task_workitem(Global_Values.Department_collectionlist.get(i).get(1).toString(),Global_Values.Department_collectionlist.get(i).get(0).toString(),Global_Values.Department_collectionlist.get(i).get(2).toString(),Global_Values.Department_collectionlist.get(i).get(3).toString());
                        users.add(t_wi);
                    }
                }
                Global_Values.task_workitems = users;
            }
            Intent i = new Intent(this, ListActivity_Task.class);
            startActivity(i);
        }else if(Global_Values.stateORdepartement == 2) {
            if(position > -1){

                ArrayList<bug_workitem> users = new ArrayList<bug_workitem>();
                ArrayList<String> users_check = new ArrayList<String>();
                int wi_count=0;
                for (int i = 0; i < Global_Values.Selected_Status_collectionlist.size(); i++) {
                    String name = Global_Values.users_Statis.get(position).mTitle;
                    if(name.equals(Global_Values.Selected_Status_collectionlist.get(i).get(0).toString())) {
                        wi_count++;
                        bug_workitem t_wi = new bug_workitem(Global_Values.Selected_Status_collectionlist.get(i).get(1).toString(),Global_Values.Selected_Status_collectionlist.get(i).get(0).toString(),Global_Values.Selected_Status_collectionlist.get(i).get(2).toString(),Global_Values.Selected_Status_collectionlist.get(i).get(3).toString());
                        users.add(t_wi);
                    }
                }
                Global_Values.bug_workitems = users;
            }
            Global_Values.GroupList_Bug = Global_Values.bug_workitems;
            Intent i = new Intent(this, ListActivity.class);
            startActivity(i);
        }

        }



    public class UserItemAdapter extends ArrayAdapter<Item> {
        private ArrayList<Item> users;
        public UserItemAdapter(Context context, int textViewResourceId, ArrayList<Item> users) {
            super(context, textViewResourceId, users);
            this.users = users;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.userlistitem, null);
            }

            Item user = users.get(position);
            if (user != null) {
                TextView username = (TextView) v.findViewById(R.id.tvName);
                TextView description = (TextView) v.findViewById(R.id.tvDesc);

                if (username != null) {
                    username.setText(user.mTitle);
                }

                if(description != null) {
                    description.setText("Task WI Count: " + user.mDescription );
                }
            }
            return v;
        }
    }



}
