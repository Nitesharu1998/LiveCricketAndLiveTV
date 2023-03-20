package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.livecrickettvscores.Activities.Fragments.CareerFragment;
import com.example.livecrickettvscores.Activities.Fragments.InfoFragment;
import com.example.livecrickettvscores.Activities.Fragments.PlayerNewsFragment;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;

public class PlayerInformation extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_playername;
    TabLayout tabLayout;
    Activity activity;
    Context context;
    Integer playerID;
    FrameLayout fl_mainframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_information);
        initView();
        initListener();
        setUpTabs();
    }

    private void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        refreshFragment(new InfoFragment(playerID));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        refreshFragment(new CareerFragment(playerID));
                        break;
                    case 4:
                        refreshFragment(new PlayerNewsFragment(playerID));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void refreshFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_mainframe, fragment).commit();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_playername = findViewById(R.id.tv_playername);
        tabLayout = findViewById(R.id.tablayout);
        fl_mainframe = findViewById(R.id.fl_mainframe);
        activity = PlayerInformation.this;
        context = getApplicationContext();
        playerID = getIntent().getIntExtra("playerID", 0);
    }

    private void setUpTabs() {
        //TODO static part
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Batting"));
        tabLayout.addTab(tabLayout.newTab().setText("Bowling"));
        tabLayout.addTab(tabLayout.newTab().setText("Career"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));

    }
}