package com.yalantis.guillotine.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.sample.R;
import com.yalantis.guillotine.sample.widget.CanaroTextView;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * Created by Dmytro Denysenko on 5/4/15.
 */
public class MainActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    CanaroTextView txt_Register, txt_About, txt_Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        ButterKnife.bind(this);

        // Main Activity Controls Init
        txt_Register = (CanaroTextView) findViewById(R.id.register);
        txt_About = (CanaroTextView) findViewById(R.id.about);
        txt_Search = (CanaroTextView) findViewById(R.id.search);

        //Adding Clickable Events
        // Register Click Event
        txt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call Register Activity
                Intent obj_Intent = new Intent(MainActivity.this, SurveyActivity.class);
                MainActivity.this.startActivity(obj_Intent);
            }
        });

        // Search Click Event
        txt_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call Search Activity
                Intent obj_Intent = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(obj_Intent);
            }
        });
        // Adding Widget to toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        final View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        // About Click Event
        txt_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call About Activity
                new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                        .setStartDelay(RIPPLE_DURATION)
                        .setActionBarViewForAnimation(toolbar)
                        .setClosedOnStart(true)
                        .build().open();
            }
        });
    }
}
