package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = PrivacyPolicyActivity.this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_privacy_policy);

        AdUtils.showNativeAd(activity, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), binding.nativeAds, false);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(activity, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        onBackPressed();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        AdUtils.showInterstitialAd(activity, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                startActivity(new Intent(activity, AppHomeActivity.class));
            }
        });
        super.onBackPressed();
    }
}