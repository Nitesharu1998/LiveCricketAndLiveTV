package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Fragments.CareerFragment;
import com.example.livecrickettvscores.Activities.Fragments.InfoFragment;
import com.example.livecrickettvscores.Activities.Fragments.PlayerNewsFragment;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;

public class PlayerInformation extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_playername, tv_playercountry;
    ImageView civ_playerimage;
    TabLayout tabLayout;
    Activity activity;
    Context context;
    String playerURL, playerName, playerImage, playerCountry;
    FrameLayout fl_mainframe;
    LinearLayout native_ads;

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
                onBackPressed();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        AdUtils.showInterstitialAd(PlayerInformation.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                refreshFragment(new InfoFragment(playerURL, playerImage, playerName, playerCountry));
                            }
                        });

                        break;
                    case 1:
                        AdUtils.showInterstitialAd(PlayerInformation.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                refreshFragment(new CareerFragment(playerURL));

                            }
                        });

                        break;
                    case 2:
                        AdUtils.showInterstitialAd(PlayerInformation.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                refreshFragment(new PlayerNewsFragment(playerURL));
                            }
                        });

                        break;
                    case 3:
                        break;
                    case 4:

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
        civ_playerimage = findViewById(R.id.civ_playerimage);
        tv_playercountry = findViewById(R.id.tv_playercountry);
        tabLayout = findViewById(R.id.tablayout);
        fl_mainframe = findViewById(R.id.fl_mainframe);
        activity = PlayerInformation.this;
        context = getApplicationContext();

        playerURL = getIntent().getStringExtra("playerURL");
        playerImage = getIntent().getStringExtra("playerImage");
        Global.sout("playerImage", playerImage);
        playerName = getIntent().getStringExtra("playerName");
        playerCountry = getIntent().getStringExtra("playerCountry");
        tv_playername.setText(playerName);
        tv_playercountry.setText(playerCountry);
        if (playerImage.contains("50x50")) {
            Glide.with(activity).load(playerImage.replace("50x50", "150x150")).error(R.drawable.defaultavatar).into(civ_playerimage);
        } else {
            Glide.with(activity).load(playerImage.replace("75x75", "150x150")).error(R.drawable.defaultavatar).into(civ_playerimage);
        }
    }
    private void setUpTabs() {
        //TODO static part
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
       /* tabLayout.addTab(tabLayout.newTab().setText("Batting"));
        tabLayout.addTab(tabLayout.newTab().setText("Bowling"));*/
        tabLayout.addTab(tabLayout.newTab().setText("Career"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));

    }
}