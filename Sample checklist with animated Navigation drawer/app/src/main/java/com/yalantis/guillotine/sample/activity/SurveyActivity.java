package com.yalantis.guillotine.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.sample.R;
import com.yalantis.guillotine.sample.data.DBConnector;
import com.yalantis.guillotine.sample.widget.CanaroTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class SurveyActivity  extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    AppCompatButton btnSubmit, btnResult;
    EditText txt_name, txt_age;
    RadioButton rdMale, rdFemale, rdMigYes, rdMigNo, rdDrugYes, rdDrugNo;
    RadioGroup genderGroup, migGroup, drugGroup;
    CanaroTextView txtViewer;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        ButterKnife.bind(this);
        context = this;
        // Survey Form Controls Init
        txt_name = (EditText) findViewById(R.id.survey_name);
        txt_age = (EditText) findViewById(R.id.survey_age);
        rdMale = (RadioButton) findViewById(R.id.male);
        rdFemale = (RadioButton) findViewById(R.id.female);
        rdMigYes = (RadioButton) findViewById(R.id.mig_yes);
        rdMigNo = (RadioButton) findViewById(R.id.mig_no);
        rdDrugYes = (RadioButton) findViewById(R.id.drug_yes);
        rdDrugNo = (RadioButton) findViewById(R.id.drug_no);
        btnSubmit = (AppCompatButton) findViewById(R.id.btn_submit);
        btnResult = (AppCompatButton) findViewById(R.id.btn_resultViewer);
        txtViewer = (CanaroTextView) findViewById(R.id.txt_viewer);
        genderGroup = (RadioGroup) findViewById(R.id.gendergroup);
        migGroup = (RadioGroup) findViewById(R.id.miggroup);
        drugGroup = (RadioGroup) findViewById(R.id.druggroup);
        // Adding Clickable Event
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(checkEmptyControls()){
                        Toast.makeText(getApplicationContext(), "Please fill all controls due to submit.", Toast.LENGTH_LONG).show();
                    }else{
                        int gender = 0; int mig = 0; int drugs = 0;
                        if(rdMale.isChecked())
                            gender = 1;
                        if(rdMigYes.isChecked())
                            mig = 1;
                        if(rdDrugYes.isChecked())
                            drugs = 1;
                        DBConnector.addNewSurvey(context,txt_name.getText().toString(),
                                Integer.parseInt(txt_age.getText().toString()),gender,mig,drugs);
                        // return back to main activity
                        Intent obj_Intent = new Intent(SurveyActivity.this, MainActivity.class);
                        SurveyActivity.this.startActivity(obj_Intent);
                    }
                }catch(Exception ex){
                    Log.e("Survey App: ", ex.getMessage());
                }
            }
        });

        // Add Click Event to See Results for the person before add it to DB
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(checkEmptyControls()){
                        Toast.makeText(getApplicationContext(), "Please fill all controls due to submit.", Toast.LENGTH_LONG).show();
                    }else{
                        int gender = 0; int mig = 0; int drugs = 0;
                        if(rdMale.isChecked())
                            gender = 1;
                        if(rdMigYes.isChecked())
                            mig = 1;
                        if(rdDrugYes.isChecked())
                            drugs = 1;
                        int result = DBConnector.getSurveyResults(Integer.parseInt(txt_age.getText().toString()),gender,mig,drugs);
                        txtViewer.setText("Mr./ Ms.: " + txt_name.getText().toString() + " has Todd’s Syndrome by " + String.valueOf(result) + "%");
                        txtViewer.setVisibility(View.VISIBLE);
                    }
                }catch (Exception ex)
                {
                    Log.e("Survey App: ", ex.getMessage());
                }
            }
        });
        // Adding Widget to toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
    }

    public boolean checkEmptyControls(){
        if(txt_name.length() > 0 && txt_age.length() > 0 && genderGroup.getCheckedRadioButtonId() != -1
                && migGroup.getCheckedRadioButtonId() != -1 && drugGroup.getCheckedRadioButtonId() != -1){
            return false;  // all controls not empty
        }
        return true; // there are some controls are empty
    }
}
