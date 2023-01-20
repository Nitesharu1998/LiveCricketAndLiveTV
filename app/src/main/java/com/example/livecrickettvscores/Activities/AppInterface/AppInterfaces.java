package com.example.livecrickettvscores.Activities.AppInterface;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LiveMatchesResponseModel;

public class AppInterfaces {
    public interface LiveMatchInterface {
        void getLiveMatchesResponseModel(LiveMatchesResponseModel liveMatchesResponseModel);
    }
}
