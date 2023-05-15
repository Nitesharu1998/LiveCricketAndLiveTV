package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.livecrickettvscores.Activities.AboutUsActivity;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.PredictionsMainActivity;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.databinding.FragmentMoreBinding;


public class MoreFragment extends Fragment {
    FragmentMoreBinding binding;
    Context context;

    @Override
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        context = binding.getRoot().getContext();

        AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), binding.nativeAds, false);


        binding.tvFantacycricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        startActivity(new Intent(context, PredictionsMainActivity.class));
                    }
                });

            }
        });
        binding.tvAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        startActivity(new Intent(context, AboutUsActivity.class));
                    }
                });
            }
        });


        return binding.getRoot();
    }
}