package com.example.livecrickettvscores.Activities.FirebaseADHandlers;

import androidx.annotation.NonNull;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.PreferencesManager.AppPreferencesManger;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (!remoteMessage.getData().isEmpty()) {
            if (remoteMessage.getData().containsValue("10")) {
                FirebaseUtils.initiateAndStoreFirebaseRemoteConfig(getApplicationContext(), new AppInterfaces.AdDataInterface() {
                    @Override
                    public void getAdData(AdsJsonPOJO adsJsonPOJO) {
                        AppPreferencesManger appPreferencesManger = new AppPreferencesManger(getApplicationContext());
                        appPreferencesManger.setAdsModel(adsJsonPOJO);
                    }
                });
            }
        }

    }
}
