package com.example.livecrickettvscores.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.livecrickettvscores.Activities.Fragments.FixtureFragment;
import com.example.livecrickettvscores.Activities.Fragments.HomeFragment;
import com.example.livecrickettvscores.Activities.Fragments.MoreFragment;
import com.example.livecrickettvscores.Activities.Fragments.StatsFragment;
import com.example.livecrickettvscores.Activities.Fragments.VideosFragment;
import com.example.livecrickettvscores.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AppHomeActivity extends AppCompatActivity {
    BottomNavigationView appbottomnav;
    FrameLayout fl_mainframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        appbottomnav = findViewById(R.id.appbottomnav);
        fl_mainframe = findViewById(R.id.fl_mainframe);

        appbottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setUpBottomNavigation(item.getItemId());
                return true;
            }
        });
        appbottomnav.setSelectedItemId(R.id.stats);
        appbottomnav.performClick();
        setUpBottomNavigation(R.id.stats);
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
                refreshFragment(new VideosFragment());
                break;
            case R.id.stats:
                refreshFragment(new StatsFragment());
                break;
            case R.id.more:
                refreshFragment(new MoreFragment());
                break;

        }
    }

    private void refreshFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_mainframe, fragment).commit();
    }
}