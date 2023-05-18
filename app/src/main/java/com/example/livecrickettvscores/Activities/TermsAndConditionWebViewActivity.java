package com.example.livecrickettvscores.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityTermsAndConditionWebViewBinding;

public class TermsAndConditionWebViewActivity extends AppCompatActivity {
    ActivityTermsAndConditionWebViewBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = TermsAndConditionWebViewActivity.this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_terms_and_condition_web_view);
        AdUtils.showNativeAd(activity, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), binding.nativeAds, false);
        binding.wvTermsCondition.getSettings().setJavaScriptEnabled(true);
        binding.wvTermsCondition.loadUrl(Constants.TermsAndConditionURL);

        binding.ivNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.wvTermsCondition.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AdUtils.showInterstitialAd(activity, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                startActivity(new Intent(activity, AppHomeActivity.class));
            }
        });
    }
}