package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdsJsonPOJO;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.FirebaseUtils;
import com.example.livecrickettvscores.Activities.PreferencesManager.AppPreferencesManger;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;
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

        Global.sout("Crickbuzz base>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("https://unofficial-cricbuzz.p.rapidapi.com/"));
        Global.sout("Crickbuzz rapid api key>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("9008284513msh0c98ccd4665e950p16b992jsnaa622c8d33b1"));
        Global.sout("Crickbuzz rapid host>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("unofficial-cricbuzz.p.rapidapi.com"));
        Activity activity = SplashActivity.this;
        AppPreferencesManger appPreferences = new AppPreferencesManger(activity);
        AppPreferencesManger appPreferencesManger = new AppPreferencesManger(this);
        FirebaseApp.initializeApp(activity);
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.ADSJSON);

        Constants.adsJsonPOJO = Global.getAdsData(appPreferencesManger.getAdsModel());

        if (Constants.adsJsonPOJO != null && !StringUtils.isNull(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getValue())) {
            Constants.adsJsonPOJO = Global.getAdsData(appPreferencesManger.getAdsModel());
            //Constants.hitCounter = Integer.parseInt(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getHits());
            AdUtils.showAppOpenAd(activity, new AppInterfaces.AppOpenADInterface() {
                @Override
                public void appOpenAdState(boolean state_load) {
                    Thread mSplashThread = new Thread() {
                        @Override
                        public void run() {
                            synchronized (this) {
                                Intent intent;
                                try {
                                    wait(3000);
                                } catch (InterruptedException e) {
                                    Global.sout("running thread interrupted", e.getLocalizedMessage());
                                } finally {
                                    finish();
                                    intent = new Intent(activity, AppHomeActivity.class);
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
                    //Constants.hitCounter = Integer.parseInt(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getHits());
                    AdUtils.showAppOpenAd(activity, new AppInterfaces.AppOpenADInterface() {
                        @Override
                        public void appOpenAdState(boolean state_load) {
                            Thread mSplashThread = new Thread() {

                                @Override
                                public void run() {
                                    synchronized (this) {
                                        Intent intent;
                                        try {
                                            wait(3000);
                                        } catch (InterruptedException e) {
                                            Global.sout("running thread interrupted", e.getLocalizedMessage());
                                        } finally {
                                            finish();
                                            intent = new Intent(activity, AppHomeActivity.class);
                                            startActivity(intent);
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


}