package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.livecrickettvscores.R;
public class BattingBowlingStats extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_batting_bowling_stats, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
    }
}