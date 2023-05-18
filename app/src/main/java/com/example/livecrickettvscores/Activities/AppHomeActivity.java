package com.example.livecrickettvscores.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.livecrickettvscores.Activities.Fragments.StatsFragment;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.videoplayer.VideoPlayerActivity;
import com.example.livecrickettvscores.BuildConfig;
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
    ImageView iv_nav;
    boolean doubleBackToExitPressedOnce = false;
    private boolean isRankingActive = false;

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
        AdUtils.showNativeAd(this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), ll_nativeAds, false);
        iv_nav = findViewById(R.id.iv_nav);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setUpBottomNavigation(R.id.rb_home);
        rg_bottomNav.getChildAt(0).performClick();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.sidenav_privacy:
                        AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                startActivity(new Intent(AppHomeActivity.this, PrivacyPolicyActivity.class));
                            }
                        });
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_terms_conditions:
                        AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                startActivity(new Intent(AppHomeActivity.this, TermsAndConditionWebViewActivity.class));
                            }
                        });
                        drawerLayout.close();
                        break;

                    case R.id.sidenav_share:
                        shareApp();
                        drawerLayout.close();
                        break;

                    case R.id.sidenav_rateus:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_prediction:
                        AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                startActivity(new Intent(AppHomeActivity.this, PredictionsMainActivity.class));
                                drawerLayout.close();
                            }
                        });
                        break;
                    case R.id.sidenav_rankings:
                        AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                isRankingActive = true;
                                refreshFragment(new StatsFragment());
                                drawerLayout.close();
                            }
                        });
                        break;
                }
                return true;
            }
        });

        iv_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
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

    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Via"));
        } catch (Exception e) {
            //e.toString();
        }

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
                finish();
                break;
            case R.id.rb_stats:
                refreshFragment(new RankingPlayersFragment());
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (isRankingActive) {
            AdUtils.showInterstitialAd(AppHomeActivity.this, new AppInterfaces.InterStitialADInterface() {
                @Override
                public void adLoadState(boolean isLoaded) {
                    isRankingActive = false;
                    startActivity(new Intent(AppHomeActivity.this, AppHomeActivity.class));
                }
            });
            return;
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void refreshFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_mainframe, fragment).commit();
    }
}