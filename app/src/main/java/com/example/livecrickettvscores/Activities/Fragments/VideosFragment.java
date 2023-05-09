package com.example.livecrickettvscores.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.R;

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