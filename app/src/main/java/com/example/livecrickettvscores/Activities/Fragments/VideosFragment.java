package com.example.livecrickettvscores.Activities.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.livecrickettvscores.Activities.Adapters.viewpagerAdapater;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VideosFragment extends Fragment {
    ViewPager vp_videos;
    TabLayout tablayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initViews(container);
        setUpViewpager();
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    private void setUpViewpager() {
        tablayout.setupWithViewPager(vp_videos);
        viewpagerAdapater viewpagerAdpater = new viewpagerAdapater(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpagerAdpater.addFragment(new AllVideosFragment(), "All Videos");
        vp_videos.setAdapter(viewpagerAdpater);
    }

    private void initViews(ViewGroup container) {
        vp_videos = container.findViewById(R.id.vp_videos);
        tablayout = container.findViewById(R.id.tablayout);
    }
}