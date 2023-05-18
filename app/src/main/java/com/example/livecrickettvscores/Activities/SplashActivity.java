package com.example.livecrickettvscores.Activities;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdsJsonPOJO;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.FirebaseUtils;
import com.example.livecrickettvscores.Activities.PreferencesManager.AppPreferencesManger;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.StringUtils;
import com.example.livecrickettvscores.databinding.ActivitySplashBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Activity activity = SplashActivity.this;
        AppPreferencesManger appPreferences = new AppPreferencesManger(activity);
        AppPreferencesManger appPreferencesManger = new AppPreferencesManger(this);
        FirebaseApp.initializeApp(activity);
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.ADSJSON);
        Constants.adsJsonPOJO = Global.getAdsData(appPreferencesManger.getAdsModel());

        AppAsyncTasks.GetFlags getFlags = new AppAsyncTasks.GetFlags(activity);
        getFlags.execute();


        if (Constants.adsJsonPOJO != null && !StringUtils.isNull(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getValue())) {
            Constants.adsJsonPOJO = Global.getAdsData(appPreferencesManger.getAdsModel());
            Constants.adsJsonPOJO.getParameters().getShowAd().getDefaultValue().setValue("false");
            AdUtils.showAppOpenAd(activity, new AppInterfaces.AppOpenADInterface() {
                @Override
                public void appOpenAdState(boolean state_load) {
                    Thread mSplashThread = new Thread() {
                        @Override
                        public void run() {
                            synchronized (this) {

                                try {
                                    wait(1000);
                                } catch (InterruptedException e) {
                                    Global.sout("running thread interrupted", e.getLocalizedMessage());
                                } finally {
                                    Intent intent = appPreferencesManger.getIsFirstRun() ? new Intent(SplashActivity.this, AppHomeActivity.class) : new Intent(SplashActivity.this, TermsAndConditionActivity.class);
                                    ;
                                    //MyApplication.getInstance().getOneTimeWorkRequest();
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        }

                    };
                    mSplashThread.start();
                }
            });

        } else {
            FirebaseUtils.initiateAndStoreFirebaseRemoteConfig(activity, new AppInterfaces.AdDataInterface() {
                @Override
                public void getAdData(AdsJsonPOJO adsJsonPOJO) {
                    //Need to call this only once per
                    appPreferencesManger.setAdsModel(adsJsonPOJO);
                    Constants.adsJsonPOJO = adsJsonPOJO;
                    AdUtils.showAppOpenAd(activity, new AppInterfaces.AppOpenADInterface() {
                        @Override
                        public void appOpenAdState(boolean state_load) {
                            Thread mSplashThread = new Thread() {

                                @Override
                                public void run() {
                                    synchronized (this) {
                                        Intent intent;
                                        try {
                                            wait(1000);
                                        } catch (InterruptedException e) {
                                            Global.sout("running thread interrupted", e.getLocalizedMessage());
                                        } finally {
                                            intent = appPreferencesManger.getIsFirstRun() ? new Intent(SplashActivity.this, AppHomeActivity.class) : new Intent(SplashActivity.this, TermsAndConditionActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }

                            };
                            mSplashThread.start();
                        }
                    });
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e( "onRestart: ", "Splash restart");
        loadInitialInterstitialAds(SplashActivity.this);
    }
}