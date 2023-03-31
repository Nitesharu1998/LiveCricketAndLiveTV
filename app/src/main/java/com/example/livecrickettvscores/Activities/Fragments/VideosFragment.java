package com.example.livecrickettvscores.Activities.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.livecrickettvscores.Activities.Adapters.viewpagerAdapater;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jsoup.select.Elements;

public class VideosFragment extends Fragment {
    RecyclerView rcl_videos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initViews(container);
        callVideosFromYoutube();
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    private void callVideosFromYoutube() {

    }

    private void initViews(ViewGroup container) {
        rcl_videos = container.findViewById(R.id.rcl_videos);
    }
}