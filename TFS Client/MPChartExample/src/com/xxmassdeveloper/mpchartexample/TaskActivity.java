package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.xxmassdeveloper.mpchartexample.R;

import java.util.List;

/**
 * Created by hmoussa on 25/03/2015.
 */
public class TaskActivity extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerOsversions;
    TextView selVersion;
    private String[] state = { "Cupcake", "Donut", "Eclair", "Froyo",
            "Gingerbread", "HoneyComb", "IceCream Sandwich", "Jellybean",
            "kitkat" };
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        spinnerOsversions = (Spinner) findViewById(R.id.spinnerstate);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
        spinnerOsversions.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinnerOsversions.setSelection(position);
        String selState = (String) spinnerOsversions.getSelectedItem();
        //selVersion.setText("Selected Android OS:" + selState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
