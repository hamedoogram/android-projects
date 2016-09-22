package com.yalantis.guillotine.sample.activity;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class SearchResultsActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    EditText txtSearch;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        ButterKnife.bind(this);

        // Search Results Activity Controls Init
        recyclerView= (RecyclerView) findViewById(R.id.results_recycler_view);

        RecyclerAdapter adapter=new RecyclerAdapter(SearchResultsActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //Layout manager for Recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));

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
