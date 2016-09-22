package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xxmassdeveloper.mpchartexample.R;

/**
 * Created by hmoussa on 25/03/2015.
 */
public class MainMenu extends Activity {
    Button btn_mywork;
    Button btn_TestUsers;
    Button btn_settings;
    Button btn_query;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        btn_mywork = (Button) findViewById(R.id.menubtn_mywork);
        btn_mywork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, ListViewMultiChartActivity.class);
                startActivity(i);
            }
        });

        btn_TestUsers = (Button) findViewById(R.id.menubtn_wi);
        btn_TestUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, TFSUsers.class);
                startActivity(i);
            }
        });

        btn_settings = (Button) findViewById(R.id.menubtn_sttings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, Settings.class);
                startActivity(i);
            }
        });

        btn_query = (Button) findViewById(R.id.menubtn_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, QueryActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
