package com.example.livecrickettvscores.Activities.AppInterface;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;

public class AppInterfaces {
    public interface LiveMatchInterface {
        void getLiveMatchesResponseModel(FixturesResponseModel fixturesResponseModel);
    }

    public interface FixturesInterface {
        void getAllMatchesData(FixturesResponseModel fixturesResponseModel);
    }
}
