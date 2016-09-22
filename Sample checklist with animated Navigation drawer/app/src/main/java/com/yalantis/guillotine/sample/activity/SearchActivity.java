package com.yalantis.guillotine.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.survey.library.appprocess.process.Survey;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.sample.R;
import com.yalantis.guillotine.sample.data.DBConnector;
import com.yalantis.guillotine.sample.model.RecyclerAdapter;
import com.yalantis.guillotine.sample.util.GenericValues;
import com.yalantis.guillotine.sample.widget.CanaroTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HamedooGram on 9/5/16.
 */
public class SearchActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    EditText txtSearch;
    AppCompatButton btnSearch;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ButterKnife.bind(this);

        // Search Activity Controls Init
        txtSearch = (EditText) findViewById(R.id.txt_search);
        btnSearch = (AppCompatButton) findViewById(R.id.btn_search);

        //Adding Clickable Events
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(checkEmptyControls()){
                        Toast.makeText(getApplicationContext(), "Please fill all controls due to submit.", Toast.LENGTH_LONG).show();
                    }else{
                        //Call search method from dbconnector
                        List<Survey> result = DBConnector.searchSurveys(SearchActivity.this, txtSearch.getText().toString());

                        // Check if there were any records retrieved.
                        if(result.size() > 0){
                            //Add Result list to Generic values "this was a good way to share some values between activities instead of using Intents and go with casting problems."
                            GenericValues.searchResult = result;
                            Intent obj_Intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
                            SearchActivity.this.startActivity(obj_Intent);

                        }else{
                            Toast.makeText(getApplicationContext(), "There weren't records found by this name.", Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception ex)
                {
                    Log.e("Survey App: ", ex.getMessage());
                }
            }
        });
        // Register Click Event

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
        if(txtSearch.length() > 0){
            return false;  // all controls not empty
        }
        return true; // there are some controls are empty
    }
}
