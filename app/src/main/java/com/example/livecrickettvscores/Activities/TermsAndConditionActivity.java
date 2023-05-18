package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.PreferencesManager.AppPreferencesManger;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityTermsAndConditionBinding;

public class TermsAndConditionActivity extends AppCompatActivity {
    ActivityTermsAndConditionBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = TermsAndConditionActivity.this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_terms_and_condition);
        AdUtils.showNativeAd(activity, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), binding.nativeAds,false);
        binding.tvPolicy1.setText(Html.fromHtml("Following link : <a href=\"https://clickmediainc.blogspot.com/2023/03/terms-conditions.html\"> <b> Terms and conditions of use </b> </a> "));
        this.binding.tvPolicy1.setLinkTextColor(getResources().getColor(R.color.light_blue));
        this.binding.tvPolicy1.setMovementMethod(LinkMovementMethod.getInstance());
        this.binding.tvPolicy2.setText(Html.fromHtml("Following Link : <a href=\"https://mediaxadvert.blogspot.com/2023/03/privacy-policy.html\"> <b> Privacy policy </b> </a> "));
        this.binding.tvPolicy2.setLinkTextColor(getResources().getColor(R.color.light_blue));
        this.binding.tvPolicy2.setMovementMethod(LinkMovementMethod.getInstance());
        this.binding.tvPolicy3.setText(Html.fromHtml("Following Link : <a href=\"https://clickmediainc.blogspot.com/2023/03/app-community-guidelines.html\"> <b> App Community Guidelines </b> </a> "));
        this.binding.tvPolicy3.setLinkTextColor(getResources().getColor(R.color.light_blue));
        this.binding.tvPolicy3.setMovementMethod(LinkMovementMethod.getInstance());
        this.binding.tvDiffaccept.setText(Html.fromHtml("By pressing the <b> Accept </b> button, I declare I have read and accepted the following condition of use:"));
        this.binding.tvDiffaccept.setLinkTextColor(getResources().getColor(R.color.light_blue));
        this.binding.tvDiffaccept.setMovementMethod(LinkMovementMethod.getInstance());

        binding.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreferencesManger manger = new AppPreferencesManger(TermsAndConditionActivity.this);
                manger.setIsFirstRun(true);
                startActivity(new Intent(TermsAndConditionActivity.this, AppHomeActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AdUtils.showInterstitialAd(activity, isLoaded -> startActivity(new Intent(activity, AppHomeActivity.class)));
    }
}

