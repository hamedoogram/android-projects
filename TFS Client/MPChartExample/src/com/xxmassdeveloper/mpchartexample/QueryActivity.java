package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmoussa on 14/05/2015.
 */
public class QueryActivity extends Activity implements View.OnClickListener {

    private Spinner spin_type, spin_assign, spin_state;
    private Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query);
        GetAllUsers allUsers = new GetAllUsers();
        allUsers.execute();
Global_Values.QueryActiv = this;
        btnSubmit = (Button)findViewById(R.id.query_button);
        btnSubmit.setOnClickListener(this);
    }


    public void call_processes(){
        addItemsOnSpinner_type();
        addItemsOnSpinner_assign();
        addItemsOnSpinner_state();
    }
    // add items into spinner dynamically
    public void addItemsOnSpinner_type() {

        spin_type = (Spinner) findViewById(R.id.spinner_wi_type);
        List<String> list = new ArrayList<String>();
        list.add("Task");
        list.add("Bug");
        list.add("Requirement");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_type.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner_assign() {

        spin_assign = (Spinner) findViewById(R.id.spinner_wi_Assignto);
        List<String> list = new ArrayList<String>();

        for(int i=0;i<Global_Values.USerslist.size();i++) {
            list.add(Global_Values.USerslist.get(i).get(1));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_assign.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner_state() {

        spin_state = (Spinner) findViewById(R.id.spinner_wi_State);
        List<String> list = new ArrayList<String>();
        list.add("Proposed");
        list.add("Active");
        list.add("Closed");
        list.add("Resolved");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_state.setAdapter(dataAdapter);
    }

    // get the selected dropdown list value


    @Override
    public void onClick(View v) {
        spin_type = (Spinner) findViewById(R.id.spinner_wi_type);
        spin_assign = (Spinner) findViewById(R.id.spinner_wi_Assignto);
        spin_state = (Spinner) findViewById(R.id.spinner_wi_State);
        btnSubmit = (Button) findViewById(R.id.query_button);
        Global_Values.query_assignto = String.valueOf(spin_assign.getSelectedItem());
        Global_Values.query_state = String.valueOf(spin_state.getSelectedItem());
        Global_Values.query_type = String.valueOf(spin_type.getSelectedItem());
        GetQueryResults query_res = new GetQueryResults();
        query_res.execute();
    }
}
