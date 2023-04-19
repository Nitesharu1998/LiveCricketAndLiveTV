package com.example.livecrickettvscores.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Fragments.FixtureFragment;
import com.example.livecrickettvscores.Activities.Fragments.HomeFragment;
import com.example.livecrickettvscores.Activities.Fragments.RankingPlayersFragment;
import com.example.livecrickettvscores.Activities.videoplayer.VideoPlayerActivity;
import com.example.livecrickettvscores.Activities.videoplayer.fragment.YoutubeFragment;
import com.example.livecrickettvscores.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AppHomeActivity extends AppCompatActivity {
    BottomNavigationView appbottomnav;
    FrameLayout fl_mainframe;
    LinearLayout ll_nativeAds;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    RadioGroup rg_bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        appbottomnav = findViewById(R.id.appbottomnav);
        fl_mainframe = findViewById(R.id.fl_mainframe);
        ll_nativeAds = findViewById(R.id.native_ads);
        navigationView = findViewById(R.id.mNavigationView);
        rg_bottomNav = findViewById(R.id.rg_bottomNav);
        drawerLayout = findViewById(R.id.drawer);
        //AdUtils.showNativeAd(this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), ll_nativeAds, false);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setUpBottomNavigation(R.id.rb_home);
        rg_bottomNav.getChildAt(0).performClick();

        rg_bottomNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                //TODO use this for custom navigation
                AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        setUpBottomNavigation(radioGroup.getCheckedRadioButtonId());
                    }
                });

            }
        });


        /*appbottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        setUpBottomNavigation(item.getItemId());
                    }
                });

                return true;
            }
        });
        appbottomnav.setSelectedItemId(R.id.home);
        appbottomnav.performClick();
        setUpBottomNavigation(R.id.home);*/
    }

    private void setUpBottomNavigation(int itemId) {
        switch (itemId) {
            case R.id.rb_fixtures:
                refreshFragment(new FixtureFragment());
                break;
            case R.id.rb_home:
                refreshFragment(new HomeFragment());
                break;
            case R.id.rb_videos:
                startActivity(new Intent(AppHomeActivity.this, VideoPlayerActivity.class));
                break;
            case R.id.rb_stats:
                //refreshFragment(new StatsFragment());
                refreshFragment(new RankingPlayersFragment());
                break;
            /*case R.id.more:
                refreshFragment(new MoreFragment());
                break;*/
        }
    }

    private void refreshFragment(YoutubeFragment youtubeFragment) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_mainframe, youtubeFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void refreshFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_mainframe, fragment).commit();
    }
}