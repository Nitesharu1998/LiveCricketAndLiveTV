package com.example.livecrickettvscores.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Fragments.FixtureFragment;
import com.example.livecrickettvscores.Activities.Fragments.HomeFragment;
import com.example.livecrickettvscores.Activities.Fragments.MoreFragment;
import com.example.livecrickettvscores.Activities.Fragments.StatsFragment;
import com.example.livecrickettvscores.Activities.Fragments.VideosFragment;
import com.example.livecrickettvscores.Activities.videoplayer.VideoPlayerActivity;
import com.example.livecrickettvscores.Activities.videoplayer.fragment.YoutubeFragment;
import com.example.livecrickettvscores.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AppHomeActivity extends AppCompatActivity {
    BottomNavigationView appbottomnav;
    FrameLayout fl_mainframe;
    LinearLayout ll_nativeAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        appbottomnav = findViewById(R.id.appbottomnav);
        fl_mainframe = findViewById(R.id.fl_mainframe);
        ll_nativeAds = findViewById(R.id.native_ads);
        //AdUtils.showNativeAd(this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), ll_nativeAds, false);

        appbottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
        setUpBottomNavigation(R.id.home);
    }

    private void setUpBottomNavigation(int itemId) {
        switch (itemId) {
            case R.id.fixture:
                refreshFragment(new FixtureFragment());
                break;
            case R.id.home:
                refreshFragment(new HomeFragment());
                break;
            case R.id.videos:
               startActivity(new Intent(AppHomeActivity.this, VideoPlayerActivity.class));
                break;
            case R.id.stats:
                refreshFragment(new StatsFragment());
                break;
            case R.id.more:
                refreshFragment(new MoreFragment());
                break;
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