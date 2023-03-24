package com.example.livecrickettvscores.Activities.PreferencesManager;

import android.content.Context;

import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdsJsonPOJO;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.google.gson.Gson;

public class AppPreferencesManger {
    private AppPreferences appPreference;

    public AppPreferencesManger(Context context) {
        appPreference = AppPreferences.getAppPreferences(context);
    }

    public void setAdsModel(AdsJsonPOJO adsJsonPOJO) {
        appPreference.putString(Constants.ADSJSON, new Gson().toJson(adsJsonPOJO));
    }

    public String getAdsModel() {
        return appPreference.getString(Constants.ADSJSON, "");
    }

    public boolean getIsFirstRun() {
        return appPreference.getBoolean(Constants.IS_FIRST_RUN, false);
    }

    public void setIsFirstRun(boolean isFirstRun) {
        appPreference.putBoolean(Constants.IS_FIRST_RUN, isFirstRun);
    }

    public String getName() {
        return appPreference.getString(Constants.USERNAME, "");
    }

    public void setName(String username) {
        appPreference.putString(Constants.USERNAME, username);
    }

    public Integer getAvatar() {
        return appPreference.getInt(Constants.USER_AVATAR, 0);
    }

    public void setAvatar(Integer avatarPos) {
        appPreference.putInt(Constants.USERNAME, avatarPos);
    }

}
